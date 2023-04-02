package urmat.jenaliev

import org.apache.spark.SparkContext
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD

object Train {

  def train(dataPath: String, modelPath: String)(implicit spark: SparkContext): Unit = {
    val ratingsRDD: RDD[Rating] = prepareData(dataPath)
    ratingsRDD.checkpoint()

    val model: MatrixFactorizationModel = ALS.train(ratingsRDD, 5, 20, 0.1)

    saveModel(model, modelPath)
  }

  def prepareData(path: String)(implicit spark: SparkContext): RDD[Rating] = {
    // reads data from dataPath into Spark RDD.
    val file: RDD[String] = spark.textFile(path)
    // only takes in first three fields (userID, itemID, rating).
    file.map(line =>
      line.split("\t") match {
        case Array(user, item, rate, _) => Rating(user.toInt, item.toInt, rate.toDouble)
      }
    )
  }

  def saveModel(model: MatrixFactorizationModel, path: String)(implicit spark: SparkContext): Unit =
    try model.save(spark, path)
    catch {
      //TODO: log
      case _: Exception => println("Error Happened when saving model!!!")
    } finally {}
}
