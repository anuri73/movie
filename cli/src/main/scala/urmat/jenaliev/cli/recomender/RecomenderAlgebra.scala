package urmat.jenaliev.cli.recomender

import cats.effect._
import eu.timepit.refined.types.all.PosInt

trait RecomenderAlgebra {
  def recomend(amount: PosInt): IO[ExitCode]
}
