package source

import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.apache.spark.sql._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.assessment._

final class AssessmentViewTest extends AnyWordSpecLike with Matchers with DatasetSuiteBase {
  override protected implicit def reuseContextIfPossible: Boolean = true

  "AssessmentView" should {
    "have valid code" in {
      object AssessmentView extends AssessmentView {
        override def dataset(implicit spark: SparkSession): Dataset[Assessment] = {
          import spark.implicits._
          spark.emptyDataset[Assessment]
        }
      }
      AssessmentView.dataset(spark).collect() shouldBe Array()
    }
  }
}
