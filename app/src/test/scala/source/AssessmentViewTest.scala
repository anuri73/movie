package source

import org.apache.spark.sql._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.assessment._

final class AssessmentViewTest extends AnyWordSpecLike with Matchers with SparkTest {
  "AssessmentView" should {
    "have valid code" in {
      object AssessmentView extends AssessmentView {
        override def dataset(implicit spark: SparkSession): Dataset[Assessment] = {
          import spark.implicits._
          spark.emptyDataset[Assessment]
        }
      }

      AssessmentView.dataset.collect() shouldBe Array()
    }
  }
}
