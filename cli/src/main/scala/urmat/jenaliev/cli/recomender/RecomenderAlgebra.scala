package urmat.jenaliev.cli.recomender

import cats.effect._
import eu.timepit.refined.types.all.PosInt
import urmat.jenaliev.dataset.TypedDataset

trait RecomenderAlgebra {
  def recomend(amount: PosInt): IO[TypedDataset[Recomendation]]
}
