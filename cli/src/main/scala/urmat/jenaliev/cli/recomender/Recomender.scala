package urmat.jenaliev.cli.recomender

import cats.effect.IO
import eu.timepit.refined.types.all.PosInt
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.logger.LazyLogging
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.movie.MovieView

final class Recomender(implicit spark: SparkSession) extends RecomenderAlgebra with LazyLogging {

  override def recomend(amount: PosInt): IO[TypedDataset[Recomendation]] = {
    import spark.implicits._
    for {
      recomendations <- IO(ModelView.recomend(amount.value))
      _              <- logger.info("Recomendations generated!")
      allMovies      <- IO(MovieView.typed)
    } yield recomendations
      .join(allMovies, allMovies(_.movieId) === recomendations(_.movieId), "left")
      .select(recomendations(_.movieId), allMovies(_.title), recomendations(_.rating))
      .sort(recomendations(_.rating).desc)
      .typed[Recomendation]
  }
}

object Recomender {
  def apply(implicit spark: SparkSession) = new Recomender
}
