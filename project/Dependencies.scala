import sbt._

object Dependencies {

  object Version {
    val scala2v12      = "2.12.10"
    val projectVersion = "0.1.0-SNAPSHOT"

    val cats       = "2.8.0"
    val catsEffect = "2.5.5"
    val frameless  = "0.11.1"
    val hadoop     = "3.2.0"
    val scalaTest  = "3.3.0-SNAP3"
    val slf4j      = "1.6.4"
    val spark      = "3.1.2"
  }

  def cats(project: String, version: String = Version.cats): ModuleID = "org.typelevel" %% s"cats-$project" % version
  def frameless(project: String): ModuleID = "org.typelevel"    %% s"frameless-$project" % Version.frameless
  def hadoop(project: String): ModuleID    = "org.apache.hadoop" % s"hadoop-$project"    % Version.hadoop
  def scalaTest: ModuleID                  = "org.scalatest"    %% "scalatest"           % Version.scalaTest
  def slf4j(project: String): ModuleID     = "org.slf4j"         % s"slf4j-$project"     % Version.slf4j
  def spark(project: String): ModuleID     = "org.apache.spark" %% s"spark-$project"     % Version.spark

}
