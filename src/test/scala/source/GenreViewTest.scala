package source

import org.apache.spark.sql.SparkSession
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import urmat.jenaliev.source.genre.{Genre, GenreView}
import urmat.jenaliev.spark.dataset.TypedDataset
import urmat.jenaliev.spark.dataset.TypedDatasetSyntax._

final class GenreViewTest extends AnyWordSpecLike with Matchers with SparkTest {
  "GenreView" should {
    "have valid code" in {
      object GenreView extends GenreView {
        override def source(implicit spark: SparkSession): TypedDataset[Genre] = {
          import spark.implicits._
          spark.emptyDataset[Genre].typed
        }
      }

      GenreView.data.collect() shouldBe Array()
    }
  }
}
