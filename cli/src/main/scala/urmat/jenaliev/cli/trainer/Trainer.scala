package urmat.jenaliev.cli.trainer

import cats.data.NonEmptyList
import cats.effect._
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.cli.movie.MovieId
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.assessment._

final case class Mv(movieId: Int, movieTitle: String)

final class Trainer(implicit spark: SparkSession) extends TrainerAlgebra {

  import spark.implicits._

  private def getAssessments: IO[TypedDataset[Assessment]] = IO(AssessmentView.typed)

  private def getUserAssessments(movieList: NonEmptyList[MovieId.MovieId]): IO[TypedDataset[Assessment]] =
    IO(
      movieList
        .map(m => Assessment(0, m.value, 5.0, ""))
        .toList
        .toDS
        .typed
    )

  override def train(userMovies: NonEmptyList[MovieId.MovieId]): IO[ExitCode] =
    for {
      assements      <- getAssessments
      userAassements <- getUserAssessments(userMovies)
      assements      <- IO(Seq(assements, userAassements).reduce(_ union _))
      distinct       <- IO(assements.distinct())
      _              <- IO(ModelView.train(distinct))
    } yield ExitCode.Success
}

object Trainer {
  def apply(implicit spark: SparkSession) = new Trainer
}
