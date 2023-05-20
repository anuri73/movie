package urmat.jenaliev.spark

import org.apache.spark.sql.{Dataset, Encoder, SparkSession}
import urmat.jenaliev.source.Ml100kData

object CSV {

  def read[T: Encoder](path: String)(implicit spark: SparkSession): Dataset[T] =
    spark.read
      .option("header", "true")
      .option("delimiter", ",")
      .option("ignoreLeadingWhiteSpace", "true")
      .option("ignoreTrailingWhiteSpace", "true")
      .schema(spark.emptyDataset[T].schema)
      .csv(Ml100kData.getMlDataPath(path))
      .as[T]
}
