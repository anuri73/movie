package urmat.jenaliev.model

import org.apache.hadoop.fs.FileSystem
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.source.assessment.Assessment
import urmat.jenaliev.source.movie.MovieId
import urmat.jenaliev.spark.dataset.TypedDataset

abstract class ModelView {
  def path: String
  def checkpointPath: String

  def train(assesments: TypedDataset[Assessment], overwrite: Boolean = true)(implicit spark: SparkSession): Unit = {
    import spark.implicits._
    import spark.implicits._

    val ratings = assesments.map((a: Assessment) => Rating(a.userId, a.itemId, a.rating)).as[Rating]

    val fs = FileSystem.get(new java.net.URI(path), spark.sparkContext.hadoopConfiguration)
    if (overwrite) {
      fs.delete(new org.apache.hadoop.fs.Path(path), true)
    }
    fs.mkdirs(new org.apache.hadoop.fs.Path(path))

    spark.sparkContext.setCheckpointDir(checkpointPath)

    ALS
      .train(ratings.rdd, 5, 20, 0.1)
      .save(spark.sparkContext, ModelView.path)
  }

  def recomend(userId: Int)(implicit spark: SparkSession): Array[MovieId] = {
    val model = MatrixFactorizationModel.load(spark.sparkContext, path)
    model.recommendProducts(userId, 10).map(p => MovieId(p.product))
  }
}

object ModelView extends ModelView {
  override def path: String           = "hdfs://namenode:9000/data/model/als_model"
  override def checkpointPath: String = "hdfs://namenode:9000/data/checkpoint/als"
}
