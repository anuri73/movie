import sbt.Keys._
import sbt._

object Dependencies {

  object Version {
    val scala2v12      = "2.12.15"
    val projectVersion = "0.1.0-SNAPSHOT"

    val spark = Def.setting {
      scalaVersion.value match {
        case `scala2v12` => "3.2.0"
      }
    }

  }

  def sparkCore(sparkVersion: String): ModuleID = "org.apache.spark" %% "spark-core"  % sparkVersion
  def sparkMl(sparkVersion: String): ModuleID   = "org.apache.spark" %% "spark-mllib" % sparkVersion
  def sparkSql(sparkVersion: String): ModuleID  = "org.apache.spark" %% "spark-sql"   % sparkVersion
  def sparkHive(sparkVersion: String): ModuleID = "org.apache.spark" %% "spark-hive"  % sparkVersion

}
