package urmat.jenaliev

import org.apache.spark.sql.SparkSession
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.assessment.AssessmentView
import urmat.jenaliev.spark.Session

object App {

  implicit protected lazy val spark: SparkSession = Session.remoteSpark

  def main(args: Array[String]): Unit =
    try ModelView.train(
      AssessmentView.data
    )
    finally spark.close

}
