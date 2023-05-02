package urmat.jenaliev.spark.dataset

import org.apache.spark.sql._
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder

import java.sql.Timestamp
import scala.language.higherKinds

object TypedDatasetSyntax {

  implicit def identity[X]: TypeRelation[X, X] = new TypeRelation[X, X] {}

  implicit def option[T[_] <: Option[_], R]: TypeRelation[T[R], R] = new TypeRelation[T[R], R] {}

  implicit class DataframeSyntax(dataframe: DataFrame) {
    def typed[T: Encoder]: TypedDataset[T] = TypedDataset(dataframe.as[T], None)

    def aliased[T: Encoder](alias: String): TypedDataset[T] = TypedDataset(dataframe.as[T].as(alias), Some(alias))
  }

  implicit class DatasetSyntax[T: Encoder](dataset: Dataset[T]) {
    def typed: TypedDataset[T] = TypedDataset(dataset, None)

    def aliased(alias: String): TypedDataset[T] = TypedDataset(dataset.as(alias), Some(alias))
  }

  implicit val timestampEncoder: ExpressionEncoder[Timestamp] = ExpressionEncoder[Timestamp]

  implicit val bigDecimalEncoder: ExpressionEncoder[BigDecimal] = ExpressionEncoder[BigDecimal]

}
