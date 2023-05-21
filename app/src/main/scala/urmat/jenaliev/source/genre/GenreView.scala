package urmat.jenaliev.source.genre

import org.apache.spark.sql._
import urmat.jenaliev.source._

abstract class GenreView extends SourceEntity[Genre] {
  lazy val path = "u.genre"
  def dataset(implicit spark: SparkSession): Dataset[Genre]
}

object GenreView extends GenreView {

  override def dataset(implicit spark: SparkSession): Dataset[Genre] = {
    import spark.implicits._
    spark.read
      .textFile(Ml100kData.getMlDataPath(path))
      .as[Genre]
  }
}
