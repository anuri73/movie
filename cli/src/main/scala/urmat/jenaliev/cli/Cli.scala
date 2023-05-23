package urmat.jenaliev.cli

import org.apache.spark.sql.SparkSession
import urmat.jenaliev.cli.algebra.CliAlgebra
import urmat.jenaliev.cli.recommend._
import urmat.jenaliev.cli.trainer._
import urmat.jenaliev.logger._

final class Cli(implicit spark: SparkSession) extends CliAlgebra with LazyLogging {

  override def trainer: TrainerAlgebra = Trainer(spark)

  override def recommender: RecommenderAlgebra = Recommender(spark)
}

object Cli {
  def apply(implicit spark: SparkSession): CliAlgebra = new Cli
}
