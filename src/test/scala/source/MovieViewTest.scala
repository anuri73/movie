package source

import org.apache.spark.sql.SparkSession
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.movie.{Movie, MovieView}
import urmat.jenaliev.spark.dataset.TypedDataset
import urmat.jenaliev.spark.dataset.TypedDatasetSyntax._

final class MovieViewTest extends AnyWordSpecLike with Matchers with SparkTest {
  "MovieView" should {
    "have valid code" in {
      object MovieView extends MovieView {
        override def source(implicit spark: SparkSession): TypedDataset[Movie] = {
          import spark.implicits._
          spark.emptyDataset[Movie].typed
        }
      }

      MovieView.data.collect() shouldBe Array()
    }
  }
}
