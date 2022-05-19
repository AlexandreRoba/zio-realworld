ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.realworld"
ThisBuild / organizationName := "realworld"

val zioVersion        = "2.0.0-RC5"
val zioConfigVersion  = "3.0.0-RC8"
val zioJsonVersion    = "0.3.0-RC7"
val zioHttpVersion    = "2.0.0-RC7"
val zioQuillVersion   = "3.17.0-RC3"
val postgresVersion   = "42.3.4"
val flywayVersion     = "8.5.10"
val zioPreludeVersion = "1.0.0-RC13"
val logbackVersion    = "1.2.11"

lazy val root = project
  .in(file("."))
  .settings(
    name := "ZIO-RealWorld",
    libraryDependencies ++= Seq(
      "dev.zio"       %% "zio"             % zioVersion,
      "dev.zio"       %% "zio-test"        % zioVersion     % Test,
      "dev.zio"       %% "zio-test-sbt"    % zioVersion     % Test,
      "dev.zio"       %% "zio-prelude"     % zioPreludeVersion,
      "dev.zio"       %% "zio-config"      % zioConfigVersion,
      "dev.zio"       %% "zio-json"        % zioJsonVersion,
      "io.d11"        %% "zhttp"           % zioHttpVersion,
      "io.d11"        %% "zhttp-test"      % zioHttpVersion % Test,
      "io.getquill"   %% "quill-jdbc-zio"  % zioQuillVersion,
      "org.postgresql" % "postgresql"      % postgresVersion,
      "org.flywaydb"   % "flyway-core"     % flywayVersion,
      "ch.qos.logback" % "logback-classic" % logbackVersion
    )
  )
