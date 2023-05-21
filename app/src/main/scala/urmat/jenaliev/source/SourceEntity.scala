package urmat.jenaliev.source

import org.apache.spark.sql._
import urmat.jenaliev.dataset.TypedDataset

trait SourceEntity[T] {
  def dataset(implicit spark: SparkSession): Dataset[T]

  def typed(implicit spark: SparkSession): TypedDataset[T] =
    TypedDataset(dataset, None)
}
