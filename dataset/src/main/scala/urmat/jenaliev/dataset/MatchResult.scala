package urmat.jenaliev.dataset

sealed trait MatchResult

case object MatchSuccess extends MatchResult

final case class MatchError(errorMessage: String) extends MatchResult
