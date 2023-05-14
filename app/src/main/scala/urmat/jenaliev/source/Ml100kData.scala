package urmat.jenaliev.source

object Ml100kData {

  def getMlDataPath(file: String) =
    s"hdfs://namenode:9000/data/ml-100k/$file"
}
