import Dependencies._

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  scalaVersion := Version.scala2v12,
  version := Version.projectVersion,
  organization := "urmat.zhenaliev",
  libraryDependencies ++= Seq(
    sparkCore(Version.spark.value),
    sparkMl(Version.spark.value),
    sparkSql(Version.spark.value),
    sparkHive(Version.spark.value)
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "movie",
    commonSettings,
    Compile / run /mainClass := Some("urmat.jenaliev.App")
  )
