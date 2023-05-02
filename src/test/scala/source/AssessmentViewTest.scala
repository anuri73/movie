package source

import org.apache.spark.sql.SparkSession
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.assessment.{Assessment, AssessmentView}
import urmat.jenaliev.spark.dataset.TypedDataset
import urmat.jenaliev.spark.dataset.TypedDatasetSyntax._

final class AssessmentViewTest extends AnyWordSpecLike with Matchers with SparkTest {
  "AssessmentView" should {
    "have valid code" in {
      object AssessmentView extends AssessmentView {
        override def source(implicit spark: SparkSession): TypedDataset[Assessment] = {
          import spark.implicits._
          spark.emptyDataset[Assessment].typed
        }
      }

      AssessmentView.data.collect() shouldBe Array()
    }
  }
}
