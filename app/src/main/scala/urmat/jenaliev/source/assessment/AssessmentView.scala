package urmat.jenaliev.source.assessment

import org.apache.spark.sql._
import urmat.jenaliev.source._
import urmat.jenaliev.spark.CSV

abstract class AssessmentView extends SourceEntity[Assessment] {
  lazy val path = "ratings.csv"
}

object AssessmentView extends AssessmentView {

  override def dataset(implicit spark: SparkSession): Dataset[Assessment] = {
    import spark.implicits._
    CSV.read[Assessment](Ml100kData.getMlDataPath(path))
  }
}
