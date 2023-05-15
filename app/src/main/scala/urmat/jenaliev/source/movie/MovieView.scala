package urmat.jenaliev.source.movie

import org.apache.spark.sql._
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.source.Ml100kData

abstract class MovieView {
  lazy val path = "movies.csv"
  def dataset(implicit spark: SparkSession): Dataset[Movie]

  def typed(implicit spark: SparkSession): TypedDataset[Movie] = {
    import spark.implicits._
    dataset.typed
  }
}

object MovieView extends MovieView {

  override def dataset(implicit spark: SparkSession): Dataset[Movie] = {
    import spark.implicits._

    spark.read
      .option("header", "true")
      .option("delimiter", ",")
      .option("ignoreLeadingWhiteSpace", "true")
      .option("ignoreTrailingWhiteSpace", "true")
      .schema(spark.emptyDataset[Movie].schema)
      .csv(Ml100kData.getMlDataPath(path))
      .as[Movie]
  }
}
