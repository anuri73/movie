import Dependencies._

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  assembly / assemblyMergeStrategy := {
    case PathList("META-INF", _*) => MergeStrategy.discard
    case _                        => MergeStrategy.first
  },
  libraryDependencies ++= Seq(
    cats("core"),
    scalaTest % "test, it",
    slf4j("api"),
    spark("core")  % Provided,
    spark("mllib") % Provided,
    spark("sql")   % Provided
  ),
  organization := "urmat.zhenaliev",
  resolvers += Resolver.typesafeIvyRepo("typesafe-ivy-releases"),
  scalaVersion := Version.scala2v12,
  version := Version.projectVersion
)

lazy val root = (project in file("."))
  .settings(
    name := "movie",
    commonSettings,
    Compile / run / mainClass := Some("urmat.jenaliev.App")
  )
