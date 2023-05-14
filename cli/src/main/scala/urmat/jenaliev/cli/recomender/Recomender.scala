package urmat.jenaliev.cli.recomender

import cats.effect.{ExitCode, IO}
import eu.timepit.refined.types.all.PosInt
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.movie.MovieView

final class Recomender(implicit spark: SparkSession) extends RecomenderAlgebra {

  override def recomend(amount: PosInt): IO[ExitCode] = {
    import spark.implicits._
    val movies    = ModelView.recomend(944, amount.value)
    val allMovies = MovieView.typed
    allMovies
      .join(movies, allMovies(_.movieId) === movies(_.itemId), "inner")
      .select(allMovies(_.movieId), allMovies(_.movieTitle), movies(_.rating))
      .sort(movies(_.rating).desc)
      .show(10, truncate = false)
    IO(ExitCode.Success)
  }
}

object Recomender {
  def apply(implicit spark: SparkSession) = new Recomender
}
