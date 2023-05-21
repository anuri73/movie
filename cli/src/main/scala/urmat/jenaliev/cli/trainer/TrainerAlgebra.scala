package urmat.jenaliev.cli.trainer

import cats.data.NonEmptyList
import cats.effect.{ExitCode, IO}
import urmat.jenaliev.cli.movie.MovieId

trait TrainerAlgebra {
  def train(movies: NonEmptyList[MovieId.MovieId]): IO[ExitCode]
}
