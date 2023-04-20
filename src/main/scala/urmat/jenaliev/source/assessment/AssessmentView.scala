package urmat.jenaliev.source.assessment

import org.apache.spark.sql.{Dataset, SparkSession}

abstract class AssessmentView {
  def source(implicit spark: SparkSession): Dataset[Assessment]
  def data(implicit spark: SparkSession): Dataset[Assessment] = source
}

object AssessmentView extends AssessmentView {

  override def source(implicit spark: SparkSession): Dataset[Assessment] = {
    import spark.implicits._

    spark.read
      .textFile("hdfs://namenode:9000/data/ml-100k/u.data")
      .map(_.split("\t"))
      .map { case Array(user, item, rate, _) =>
        Assessment(user.toInt, item.toInt, rate.toDouble)
      }
  }
}
