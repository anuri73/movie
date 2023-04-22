package urmat.jenaliev.source.movie

import org.apache.spark.sql.{Dataset, SparkSession}
import urmat.jenaliev.source.Ml100kData

abstract class MovieView {
  lazy val path = "u.item"
  def source(implicit spark: SparkSession): Dataset[Movie]
  def data(implicit spark: SparkSession): Dataset[Movie] = source
}

object MovieView extends MovieView {

  override def source(implicit spark: SparkSession): Dataset[Movie] = {
    import spark.implicits._

    spark.read
      .textFile(Ml100kData.getMlDataPath(path))
      .map(_.split("\\|"))
      .map { f =>
        Movie(
          f(0).toInt,
          f(1),
          f(2),
          f(3),
          f(4),
          f(5) == "1",
          f(6) == "1",
          f(7) == "1",
          f(8) == "1",
          f(9) == "1",
          f(10) == "1",
          f(11) == "1",
          f(12) == "1",
          f(13) == "1",
          f(14) == "1",
          f(15) == "1",
          f(16) == "1",
          f(17) == "1",
          f(18) == "1",
          f(19) == "1",
          f(20) == "1",
          f(21) == "1",
          f(22) == "1"
        )
      }
  }
}
