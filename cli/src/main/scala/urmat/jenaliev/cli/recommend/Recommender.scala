package urmat.jenaliev.cli.recommend

import cats.effect.IO
import eu.timepit.refined.types.all.PosInt
import org.apache.spark.sql.SparkSession
import urmat.jenaliev.dataset.TypedDataset
import urmat.jenaliev.dataset.TypedDatasetSyntax._
import urmat.jenaliev.logger.LazyLogging
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.movie.MovieView

final class Recommender(implicit spark: SparkSession) extends RecommenderAlgebra with LazyLogging {

  override def recommend(amount: PosInt): IO[TypedDataset[Recommendation]] = {
    import spark.implicits._
    for {
      recommendations <- IO(ModelView.recommend(amount.value))
      _              <- logger.info("Recomendations generated!")
      allMovies      <- IO(MovieView.typed)
    } yield recommendations
      .join(allMovies, allMovies(_.movieId) === recommendations(_.movieId), "left")
      .select(recommendations(_.movieId), allMovies(_.title), recommendations(_.rating))
      .sort(recommendations(_.rating).desc)
      .typed[Recommendation]
  }
}

object Recommender {
  def apply(implicit spark: SparkSession) = new Recommender
}
