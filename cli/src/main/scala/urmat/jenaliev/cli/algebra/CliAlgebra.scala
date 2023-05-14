package urmat.jenaliev.cli.algebra

import urmat.jenaliev.cli.recomender.RecomenderAlgebra
import urmat.jenaliev.cli.trainer.TrainerAlgebra

trait CliAlgebra {
  def trainer: TrainerAlgebra
  def recomender: RecomenderAlgebra
}
