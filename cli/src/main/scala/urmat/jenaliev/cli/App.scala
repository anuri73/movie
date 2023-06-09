package urmat.jenaliev.cli

import cats.effect._
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.cli.Commands.subcommands
import urmat.jenaliev.spark.Session

object App
    extends CommandIOApp(
      name    = "Movie recommender",
      header  = "Movie recommendation system",
      version = "0.0.1"
    ) {

  implicit protected lazy val spark: SparkSession = Session.remoteSpark

  override def main: Opts[IO[ExitCode]] = subcommands.map {
    case TrainCmdOptions(userMovieIds) => Cli(spark).trainer.train(userMovieIds)
    case RecomendCmdOptions(amount) =>
      for {
        recommendataions <- Cli(spark).recommender.recommend(amount)
        _                <- IO(recommendataions.show(amount.value, truncate = false))
      } yield ExitCode.Success
  }
}
