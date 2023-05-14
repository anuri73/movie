package source

import org.apache.spark.sql._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.movie.{Movie, MovieView}

final class MovieViewTest extends AnyWordSpecLike with Matchers with SparkTest {
  "MovieView" should {
    "have valid code" in {
      object MovieView extends MovieView {
        override def dataset(implicit spark: SparkSession): Dataset[Movie] = {
          import spark.implicits._
          spark.emptyDataset[Movie]
        }
      }

      MovieView.dataset.collect() shouldBe Array()
    }
  }
}
