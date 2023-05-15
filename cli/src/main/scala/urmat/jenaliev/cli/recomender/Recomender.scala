package urmat.jenaliev.cli.recomender

import cats.data.NonEmptyList
import cats.effect.{ExitCode, IO}
import eu.timepit.refined.types.all.PosInt
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.cli.movie.MovieId
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.assessment.Assessment
import urmat.jenaliev.source.movie.MovieView

final class Recomender(implicit spark: SparkSession) extends RecomenderAlgebra {

  private def getUserAssessments(movieList: NonEmptyList[MovieId.MovieId]): TypedDataset[Assessment] = {
    import spark.implicits._
    val movies = MovieView.typed
    movies
      .filter(movies(_.movieId) isin (movieList.toList.map(_.value): _*))
      .map(m => Assessment(300000, m.movieId, 5.0, ""))
      .typed
  }

  override def recomend(amount: PosInt): IO[ExitCode] = {
    import spark.implicits._
    val userProducts     = Seq(71, 1, 22)
    val recomendedMovies = ModelView.recomend(userProducts)
    val allMovies        = MovieView.typed
    recomendedMovies
      .join(allMovies, allMovies(_.movieId) === recomendedMovies(_.movieId), "left")
      .select(allMovies(_.movieId), allMovies(_.title), recomendedMovies(_.rating))
      .sort(recomendedMovies(_.rating).desc)
      .show(10, truncate = false)
    IO(ExitCode.Success)
  }
}

object Recomender {
  def apply(implicit spark: SparkSession) = new Recomender
}
