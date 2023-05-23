package urmat.jenaliev.cli.recommend

import cats.effect._
import eu.timepit.refined.types.all.PosInt
import urmat.jenaliev.dataset.TypedDataset

trait RecommenderAlgebra {
  def recommend(amount: PosInt): IO[TypedDataset[Recommendation]]
}
