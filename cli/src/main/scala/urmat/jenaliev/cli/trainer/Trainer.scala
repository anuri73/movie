package urmat.jenaliev.cli.trainer

import cats.data.NonEmptyList
import cats.effect._
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.cli.movie.MovieId
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.assessment.{Assessment, AssessmentView}
import urmat.jenaliev.source.movie.MovieView

final case class Mv(movieId: Int, movieTitle: String)

final class Trainer(implicit spark: SparkSession) extends TrainerAlgebra {

  private def getAssessments: TypedDataset[Assessment] = AssessmentView.typed

  private def getUserAssessments(movieList: NonEmptyList[MovieId.MovieId]): TypedDataset[Assessment] = {
    import spark.implicits._
    val movies = MovieView.typed
    movies
      .filter(movies(_.movieId) isin (movieList.toList.map(_.value): _*))
      .map(m => Assessment(944, m.movieId, 5.0))
      .typed
  }

  override def train(movies: NonEmptyList[MovieId.MovieId]): IO[ExitCode] = {
    val assessments = getAssessments.union(getUserAssessments(movies))
    ModelView.train(assessments)
    IO(ExitCode.Success)
  }
}

object Trainer {
  def apply(implicit spark: SparkSession) = new Trainer
}
