package urmat.jenaliev.source.genre

import org.apache.spark.sql._
import urmat.jenaliev.source.Ml100kData
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._

abstract class GenreView {
  lazy val path = "u.genre"
  def dataset(implicit spark: SparkSession): Dataset[Genre]

  def typed(implicit spark: SparkSession): TypedDataset[Genre] = {
    import spark.implicits._
    dataset.typed
  }
}

object GenreView extends GenreView {

  override def dataset(implicit spark: SparkSession): Dataset[Genre] = {
    import spark.implicits._
    spark.read
      .textFile(Ml100kData.getMlDataPath(path))
      .as[Genre]
  }
}
