package urmat.jenaliev.source.genre

import org.apache.spark.sql.{Dataset, SparkSession}

abstract class GenreView {
  def source(implicit spark: SparkSession): Dataset[Genre]
  def data(implicit spark: SparkSession): Dataset[Genre] = source
}

object GenreView extends GenreView {

  override def source(implicit spark: SparkSession): Dataset[Genre] = {
    import spark.implicits._

    spark.read
      .option("sep", "\t")
      .csv("hdfs://namenode:9000/data/ml-100k/u.genre")
      .as
  }
}
