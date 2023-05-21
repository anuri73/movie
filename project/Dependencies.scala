import sbt._

object Dependencies {

  object Version {
    val scala2v12      = "2.12.10"
    val projectVersion = "0.1.0-SNAPSHOT"

    val cats             = "2.8.0"
    val catsEffect       = "3.4.0"
    val frameless        = "0.11.1"
    val hadoop           = "3.2.0"
    val log4j            = "2.20.0"
    val macroParadise    = "2.1.0"
    val scalaTest        = "3.2.16"
    val slf4j            = "1.6.4"
    val decline          = "2.4.1"
    val spark            = "3.1.2"
    val sparkTestingBase = s"${spark}_1.4.0"
    val typesafeLogging  = "3.9.4"
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

  def typesafeLogging(project: String = "", version: String = Version.typesafeLogging): ModuleID =
    "com.typesafe.scala-logging" %% Seq("scala-logging", project).filter(_.nonEmpty).mkString("-") % version

  val macroParadise    = ("org.scalamacros" % "paradise"           % Version.macroParadise).cross(CrossVersion.patch)
  val sparkTestingBase = "com.holdenkarau" %% "spark-testing-base" % Version.sparkTestingBase
}
