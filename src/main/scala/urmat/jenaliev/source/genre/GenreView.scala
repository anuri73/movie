package urmat.jenaliev.source.genre

import org.apache.spark.sql.{Dataset, SparkSession}
import urmat.jenaliev.source.Ml100kData

abstract class GenreView {
  lazy val path = "u.genre"
  def source(implicit spark: SparkSession): Dataset[Genre]
  def data(implicit spark: SparkSession): Dataset[Genre] = source
}

object GenreView extends GenreView {

  override def source(implicit spark: SparkSession): Dataset[Genre] = {
    import spark.implicits._

    spark.read
      .textFile(Ml100kData.getMlDataPath(path))
      .as
  }
}
