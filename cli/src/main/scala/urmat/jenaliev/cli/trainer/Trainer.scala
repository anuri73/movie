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

  private def getAssessments: TypedDataset[Assessment] = AssessmentView.typed

  private def getUserAssessments(movieList: NonEmptyList[MovieId.MovieId]): TypedDataset[Assessment] = {
    import spark.implicits._
    movieList
      .map(m => Assessment(0, m.value, 5.0, ""))
      .toList
      .toDS
      .typed
  }

  override def train(movies: NonEmptyList[MovieId.MovieId]): IO[ExitCode] =
    for {
      assements      <- IO(getAssessments.limit(100000))
      userAassements <- IO(getUserAssessments(movies))
      assements      <- IO(Seq(assements, userAassements).reduce(_ union _))
      _              <- IO(ModelView.train(assements.distinct()))
    } yield ExitCode.Success
}

object Trainer {
  def apply(implicit spark: SparkSession) = new Trainer
}
