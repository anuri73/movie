package urmat.jenaliev.source.assessment

import org.apache.spark.sql.SparkSession
import urmat.jenaliev.source.Ml100kData
import urmat.jenaliev.spark.dataset.TypedDataset
import urmat.jenaliev.spark.dataset.TypedDatasetSyntax._

abstract class AssessmentView {
  lazy val path = "u.data"
  def source(implicit spark: SparkSession): TypedDataset[Assessment]
  def data(implicit spark: SparkSession): TypedDataset[Assessment] = source
}

object AssessmentView extends AssessmentView {

  override def source(implicit spark: SparkSession): TypedDataset[Assessment] = {
    import spark.implicits._

    spark.read
      .textFile(Ml100kData.getMlDataPath(path))
      .map(_.split("\t"))
      .map { case Array(user, item, rate, _) =>
        Assessment(user.toInt, item.toInt, rate.toDouble)
      }
      .typed
  }
}
