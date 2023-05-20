package urmat.jenaliev.source.movie

import org.apache.spark.sql._
import urmat.jenaliev.source._
import urmat.jenaliev.spark.CSV

abstract class MovieView extends SourceEntity[Movie] {
  lazy val path = "movies.csv"
  def dataset(implicit spark: SparkSession): Dataset[Movie]
}

object MovieView extends MovieView {

  override def dataset(implicit spark: SparkSession): Dataset[Movie] = {
    import spark.implicits._
    CSV.read[Movie](Ml100kData.getMlDataPath(path))
  }
}
