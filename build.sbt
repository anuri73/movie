import Dependencies._

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  assembly / assemblyShadeRules := Seq(
    ShadeRule.rename("org.typelevel.cats.**" -> "repackaged.org.typelevel.cats.@1").inAll,
    ShadeRule.rename("cats.**" -> "repackaged.cats.@1").inAll
  ),
  assembly / assemblyMergeStrategy := {
    case PathList("META-INF", _*) => MergeStrategy.discard
    case _                        => MergeStrategy.first
  },
  assembly / assemblyOption ~= { _.withIncludeScala(includeScala = true) },
  libraryDependencies ++= Seq(
    cats("core"),
    log4j("api"),
    log4j("core"),
    log4j("slf4j-impl"),
    spark("core")  % Provided,
    spark("mllib") % Provided,
    spark("sql")   % Provided
  ),
  organization := "urmat.zhenaliev",
  resolvers += Resolver.typesafeIvyRepo("typesafe-ivy-releases"),
  scalaVersion := Version.scala2v12,
  version := Version.projectVersion
)

lazy val dataset = (project in file("dataset"))
  .settings(
    name := "dataset",
    commonSettings,
    libraryDependencies ++= Seq(
      compilerPlugin(macroParadise)
    )
  )

lazy val app = (project in file("app"))
  .settings(
    name := "app",
    commonSettings,
    libraryDependencies ++= Seq(
      scalaTest        % "test",
      sparkTestingBase % "test"
    )
  )
  .dependsOn(dataset)

lazy val cli = (project in file("cli"))
  .settings(
    assembly / assemblyJarName := "app-cli.jar",
    assembly / mainClass := Some("urmat.jenaliev.cli.App"),
    commonSettings,
    Compile / run / mainClass := Some("urmat.jenaliev.cli.App"),
    libraryDependencies ++= Seq(
      decline(),
      decline("effect"),
      decline("refined")
    ),
    name := "cli"
  )
  .dependsOn(dataset, app)
