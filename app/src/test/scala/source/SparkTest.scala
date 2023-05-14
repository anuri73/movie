package source

import org.apache.spark.sql.SparkSession
import urmat.jenaliev.spark.Session

trait SparkTest {
  implicit protected val spark: SparkSession = Session.localSpark
}
