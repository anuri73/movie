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

  private def defaultSessionBuilder: SparkSession.Builder =
    SparkSession
      .builder()
      .appName("Movie recommendation system")
      .config("spark.sql.session.timeZone", "UTC")

  private[spark] def createTempDir(prefix: String = "spark-warehouse"): Path = {
    val tempDir = FileUtils.createTempDir(prefix)
    scala.sys.addShutdownHook(FileUtils.delete(tempDir))
    tempDir
  }

  private def adjustLogLevel(): Unit =
    Seq("org", "akka").foreach(Logger.getLogger(_).setLevel(Level.ERROR))
}
