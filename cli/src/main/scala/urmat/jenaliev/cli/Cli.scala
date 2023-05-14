package urmat.jenaliev.cli

import org.apache.spark.sql.SparkSession
import urmat.jenaliev.cli.algebra.CliAlgebra
import urmat.jenaliev.cli.recomender._
import urmat.jenaliev.cli.trainer._

final class Cli(implicit spark: SparkSession) extends CliAlgebra {
  override def trainer: TrainerAlgebra = Trainer(spark)

  override def recomender: RecomenderAlgebra = Recomender(spark)
}

object Cli {
  def apply(implicit spark: SparkSession): CliAlgebra = new Cli
}
