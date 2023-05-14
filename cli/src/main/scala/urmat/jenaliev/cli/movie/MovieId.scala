package urmat.jenaliev.cli.movie

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.boolean._
import eu.timepit.refined.numeric.{Positive, _}

object MovieId {
  final case class Movie(id: MovieId)

  type MovieId = Int Refined (Positive And Less[W.`1682`.T])

  object MovieId extends RefinedTypeOps.Numeric[MovieId, Int]
}
