package urmat.jenaliev.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

import java.nio.file.Path

object Session {

  lazy val warehouseDir: Path = createTempDir()

  def remoteSpark: SparkSession = {
    adjustLogLevel()
    defaultSessionBuilder.getOrCreate()
  }

  def localSpark: SparkSession = {
    adjustLogLevel()
    defaultSessionBuilder
      .master("local")
      .config("spark.sql.shuffle.partitions", 4)
      .config("spark.sql.warehouse.dir", warehouseDir.toUri.toString)
      .config("spark.driver.bindAddress", "127.0.0.1")
      .config("spark.executor.memory", "1024m")
      .config("spark.driver.memory", "1024m")
      .getOrCreate()
  }

  private def defaultSessionBuilder: SparkSession.Builder =
    SparkSession
      .builder()
      .appName("Movie recomendation system")
      .config("spark.sql.session.timeZone", "UTC")

  private[spark] def createTempDir(prefix: String = "spark-warehouse"): Path = {
    val tempDir = FileUtils.createTempDir(prefix)
    scala.sys.addShutdownHook(FileUtils.delete(tempDir))
    tempDir
  }

  private def adjustLogLevel(): Unit =
    Seq("org", "akka").foreach(Logger.getLogger(_).setLevel(Level.ERROR))
}
