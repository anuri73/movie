package urmat.jenaliev.source.assessment

import org.apache.spark.sql._
import urmat.jenaliev.source.Ml100kData
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._

abstract class AssessmentView {
  lazy val path = "u.data"
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
      .textFile(Ml100kData.getMlDataPath(path))
      .map(_.split("\t"))
      .map { case Array(user, item, rate, _) =>
        Assessment(user.toInt, item.toInt, rate.toDouble)
      }
  }
}
