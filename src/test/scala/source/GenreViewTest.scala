package source

import org.apache.spark.sql.{Dataset, SparkSession}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.genre.{Genre, GenreView}

final class GenreViewTest extends AnyWordSpecLike with Matchers with SparkTest {
  "GenreView" should {
    "have valid code" in {
      object GenreView extends GenreView {
        override def source(implicit spark: SparkSession): Dataset[Genre] = {
          import spark.implicits._
          spark.emptyDataset
        }
      }

      GenreView.data.collect() shouldBe Array()
    }
  }
}
