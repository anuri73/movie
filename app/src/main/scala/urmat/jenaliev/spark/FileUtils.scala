package urmat.jenaliev.spark

import java.nio.file.{Files, Path}
import java.util.Comparator
import scala.collection.JavaConverters._

object FileUtils {
  private val pathReverseComparator = Comparator.reverseOrder[Path]()

  def createTempDir(prefix: String): Path = Files.createTempDirectory(prefix)

  def delete(path: Path): Unit = {
    val files = Files.walk(path)

    try files
      .sorted(pathReverseComparator)
      .iterator()
      .asScala
      .foreach(Files.delete)
    finally files.close()
  }
}
