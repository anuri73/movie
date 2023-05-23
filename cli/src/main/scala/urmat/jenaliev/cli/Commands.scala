package urmat.jenaliev.cli

import cats.data.NonEmptyList
import com.monovore.decline._
import com.monovore.decline.refined._
import eu.timepit.refined.types.numeric.PosInt
import urmat.jenaliev.cli.movie.MovieId.MovieId

sealed trait CmdOptions extends Product with Serializable

final case class TrainCmdOptions(userMovieIds: NonEmptyList[MovieId]) extends CmdOptions
final case class RecomendCmdOptions(amount: PosInt)                   extends CmdOptions

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

    val recommend = Command(
      name   = "recommend",
      header = "recommend command"
    )(
      amountOpt.map(RecomendCmdOptions)
    )

    Opts
      .subcommand(train)
      .orElse(Opts.subcommand(recommend))
  }
}
