package urmat.jenaliev.model

import org.apache.hadoop.fs.FileSystem
import org.apache.spark.mllib.recommendation._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.source.assessment.Assessment

abstract class ModelView {
  def path: String
  def checkpointPath: String

  def train(assesments: TypedDataset[Assessment], overwrite: Boolean = true)(implicit spark: SparkSession): Unit = {
    import spark.implicits._

    val fs = FileSystem.get(new java.net.URI(path), spark.sparkContext.hadoopConfiguration)
    if (overwrite) {
      fs.delete(new org.apache.hadoop.fs.Path(path), true)
    }
    fs.mkdirs(new org.apache.hadoop.fs.Path(path))

    val Array(training, validation, test) = assesments.dataset
      .randomSplit(Array(0.6, 0.2, 0.2))

    spark.sparkContext.setCheckpointDir(checkpointPath)

    val rank           = 12
    val lambda         = 0.1
    val numIter        = 20
    val model          = ALS.train(training.map(t => Rating(t.userId, t.movieId, t.rating)).rdd, rank, numIter, lambda)
    val validationRmse = computeRmse(model, validation.map(t => Rating(t.userId, t.movieId, t.rating)).rdd)
    println(
      s"RMSE (validation) = $validationRmse for the model trained with rank = $rank, lambda = $lambda, and numIter = $numIter."
    )

    val testRmse = computeRmse(model, test.map(t => Rating(t.userId, t.movieId, t.rating)).rdd)

    val meanRating   = training.union(validation).map(_.rating).rdd.mean
    val baselineRmse = math.sqrt(test.map(x => (meanRating - x.rating) * (meanRating - x.rating)).rdd.mean)
    val improvement  = (baselineRmse - testRmse) / baselineRmse * 100
    println("The best model improves the baseline by " + "%1.2f".format(improvement) + "%.")

    model.save(spark.sparkContext, ModelView.path)
  }

  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating]): Double = {
    val predictions: RDD[Rating] = model.predict(data.map(x => (x.user, x.product)))
    val test                     = data.map(x => ((x.user, x.product), x.rating))
    val predictionsAndRatings = predictions
      .map(x => ((x.user, x.product), x.rating))
      .join(test)
      .values
    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).mean)
  }

  def recomend(userMovies: Seq[Int])(implicit spark: SparkSession): TypedDataset[Assessment] = {
    import spark.implicits._
    MatrixFactorizationModel
      .load(spark.sparkContext, path)
      .predict(userMovies.map((0, _)).toDS.rdd)
      .map(p => Assessment(p.user, p.product, p.rating, ""))
      .toDS
      .typed
  }
}

object ModelView extends ModelView {
  override def path: String           = "hdfs://namenode:9000/data/model/als_model"
  override def checkpointPath: String = "hdfs://namenode:9000/data/checkpoint/als"
}
