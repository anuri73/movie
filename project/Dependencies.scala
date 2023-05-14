import sbt._

object Dependencies {

  object Version {
    val scala2v12      = "2.12.10"
    val projectVersion = "0.1.0-SNAPSHOT"

    val cats          = "2.8.0"
    val catsEffect    = "2.5.5"
    val frameless     = "0.11.1"
    val hadoop        = "3.2.0"
    val log4j         = "2.20.0"
    val macroParadise = "2.1.0"
    val scalaTest     = "3.3.0-SNAP3"
    val slf4j         = "1.6.4"
    val decline       = "2.4.1"
    val spark         = "3.1.2"
  }

  def cats(project: String, version: String = Version.cats): ModuleID =
    "org.typelevel" %% s"cats-$project" % version

  def decline(project: String = "", version: String = Version.decline): ModuleID =
    "com.monovore" %% Seq("decline", project).filter(_.nonEmpty).mkString("-") % version

  def frameless(project: String): ModuleID =
    "org.typelevel" %% s"frameless-$project" % Version.frameless

  def hadoop(project: String): ModuleID =
    "org.apache.hadoop" % s"hadoop-$project" % Version.hadoop

  def log4j(project: String): ModuleID =
    "org.apache.logging.log4j" % s"log4j-$project" % Version.log4j

  def scalaTest: ModuleID =
    "org.scalatest" %% "scalatest" % Version.scalaTest

  def slf4j(project: String): ModuleID =
    "org.slf4j" % s"slf4j-$project" % Version.slf4j

  def spark(project: String): ModuleID =
    "org.apache.spark" %% s"spark-$project" % Version.spark

  val macroParadise = ("org.scalamacros" % "paradise" % Version.macroParadise).cross(CrossVersion.patch)

}
