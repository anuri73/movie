package urmat.jenaliev.cli

import cats.data.NonEmptyList
import com.monovore.decline._
import com.monovore.decline.refined._
import eu.timepit.refined.types.numeric.PosInt
import urmat.jenaliev.cli.movie.MovieId.MovieId

sealed trait CmdOptions extends Product with Serializable

final case class TrainCmdOptions(ids: NonEmptyList[MovieId]) extends CmdOptions
final case class RecomendCmdOptions(amount: PosInt)           extends CmdOptions

object Commands {

  val subcommands: Opts[CmdOptions] = {

    val movieIdsOpts = Opts.options[MovieId]("movie-ids", "movie ids", short = "m")
    val amountOpt    = Opts.option[PosInt]("amount", "amount", short = "n")

    val train = Command(
      name   = "train",
      header = "train command"
    )(
      movieIdsOpts.map(TrainCmdOptions)
    )

    val recomend = Command(
      name   = "recomend",
      header = "recomend command"
    )(
      amountOpt.map(RecomendCmdOptions)
    )

    Opts
      .subcommand(train)
      .orElse(Opts.subcommand(recomend))
  }
}
