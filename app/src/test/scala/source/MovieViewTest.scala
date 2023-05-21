package source

import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.apache.spark.sql._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.movie._

final class MovieViewTest extends AnyWordSpecLike with Matchers with DatasetSuiteBase {
  "MovieView" should {
    "have valid code" in {
      object MovieView extends MovieView {
        override def dataset(implicit spark: SparkSession): Dataset[Movie] = {
          import spark.implicits._
          spark.emptyDataset[Movie]
        }
      }

      MovieView.dataset(spark).collect() shouldBe Array()
    }
  }
}
