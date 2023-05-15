package urmat.jenaliev.source.assessment

import org.apache.spark.sql._
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.source.Ml100kData

abstract class AssessmentView {
  lazy val path = "ratings.csv"
  def dataset(implicit spark: SparkSession): Dataset[Assessment]

  def typed(implicit spark: SparkSession): TypedDataset[Assessment] = {
    import spark.implicits._
    dataset.typed
  }
}

object AssessmentView extends AssessmentView {

  override def dataset(implicit spark: SparkSession): Dataset[Assessment] = {
    import spark.implicits._

    spark.read
      .option("header", "true")
      .option("delimiter", ",")
      .option("ignoreLeadingWhiteSpace", "true")
      .option("ignoreTrailingWhiteSpace", "true")
      .schema(spark.emptyDataset[Assessment].schema)
      .csv(Ml100kData.getMlDataPath(path))
      .as[Assessment]
  }
}
