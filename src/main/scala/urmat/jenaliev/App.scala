package urmat.jenaliev

import org.apache.spark.sql.SparkSession

object App {

  implicit lazy val spark: SparkSession = SparkSession
    .builder()
    .appName("Movie recomendation system")
    .config("spark.sql.session.timeZone", "UTC")
    .config("spark.executor.cores", 1)
    .config("spark.executor.memory", "512m")
    .master("spark://localhost:7077")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  def main(args: Array[String]): Unit = {
    import spark.implicits._
    try {
      val df = (1 to 100).toDF
      print(df.count)
    } finally spark.close
  }
}
