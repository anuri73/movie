package urmat.jenaliev

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import urmat.jenaliev.model.ModelView
import urmat.jenaliev.source.assessment.AssessmentView
import urmat.jenaliev.source.movie.{MovieId, MovieView}
import urmat.jenaliev.spark.Session

object App {

  implicit protected lazy val spark: SparkSession = Session.remoteSpark

  def main(args: Array[String]): Unit =
    try args(0) match {
      case "0" =>
        val movies: Array[Int] = recomend(args(1).toInt).map(_.id)
        MovieView.data.filter(col("movieId").isin(movies: _*)).show(10, truncate = false)
      case "1" | _ => train()
    } finally spark.close

  def train(): Unit = ModelView.train(AssessmentView.data)

  def recomend(userId: Int): Array[MovieId] = ModelView.recomend(userId)

}
