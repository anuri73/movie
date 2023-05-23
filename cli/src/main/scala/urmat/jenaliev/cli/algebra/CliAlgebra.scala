package urmat.jenaliev.cli.algebra

import urmat.jenaliev.cli.recommend.RecommenderAlgebra
import urmat.jenaliev.cli.trainer.TrainerAlgebra

trait CliAlgebra {
  def trainer: TrainerAlgebra
  def recommender: RecommenderAlgebra
}
