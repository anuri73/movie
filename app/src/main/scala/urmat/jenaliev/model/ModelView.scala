package urmat.jenaliev.model

import org.apache.hadoop.fs.FileSystem
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.source.assessment.Assessment

abstract class ModelView {
  def path: String
  def checkpointPath: String

  def train(assesments: TypedDataset[Assessment], overwrite: Boolean = true)(implicit spark: SparkSession): Unit = {
    import spark.implicits._

    val ratings = assesments.map((a: Assessment) => Rating(a.userId, a.itemId, a.rating)).as[Rating]

    val fs = FileSystem.get(new java.net.URI(path), spark.sparkContext.hadoopConfiguration)
    if (overwrite) {
      fs.delete(new org.apache.hadoop.fs.Path(path), true)
    }
    fs.mkdirs(new org.apache.hadoop.fs.Path(path))

    spark.sparkContext.setCheckpointDir(checkpointPath)

    val rank          = 20
    val numIterations = 15
    val lambda        = 0.10
    val alpha         = 1.00
    val block         = -1
    val seed          = 12345L
    val implicitPrefs = false

    val model = new ALS()
      .setIterations(numIterations)
      .setBlocks(block)
      .setAlpha(alpha)
      .setLambda(lambda)
      .setRank(rank)
      .setSeed(seed)
      .setImplicitPrefs(implicitPrefs)
      .run(ratings.rdd)

    model.save(spark.sparkContext, ModelView.path)
  }

  def recomend(userId: Int, amount: Int = 10)(implicit spark: SparkSession): TypedDataset[Assessment] = {
    import spark.implicits._
    val model = MatrixFactorizationModel.load(spark.sparkContext, path)
    model.recommendProducts(userId, amount).map(p => Assessment(userId, p.product, p.rating)).toSeq.toDS.typed
  }
}

object ModelView extends ModelView {
  override def path: String           = "hdfs://namenode:9000/data/model/als_model"
  override def checkpointPath: String = "hdfs://namenode:9000/data/checkpoint/als"
}
