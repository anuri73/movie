package urmat.jenaliev.source.genre

import org.apache.spark.sql.SparkSession
import urmat.jenaliev.source.Ml100kData
import urmat.jenaliev.spark.dataset.TypedDataset
import urmat.jenaliev.spark.dataset.TypedDatasetSyntax._

abstract class GenreView {
  lazy val path = "u.genre"
  def source(implicit spark: SparkSession): TypedDataset[Genre]
  def data(implicit spark: SparkSession): TypedDataset[Genre] = source
}

object GenreView extends GenreView {

  override def source(implicit spark: SparkSession): TypedDataset[Genre] = {
    import spark.implicits._
    spark.read
      .textFile(Ml100kData.getMlDataPath(path))
      .as[Genre]
      .typed
  }
}
