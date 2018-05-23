name := """auth"""
organization := "com.pagnoncelli"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"
val silhouetteVersion = "5.0.3"
val swaggerUIVersion = "3.6.1"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"


libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % silhouetteVersion,
  "com.mohiva" %% "play-silhouette-persistence" % silhouetteVersion,
  "com.mohiva" %% "play-silhouette-password-bcrypt" % silhouetteVersion,
  "com.mohiva" %% "play-silhouette-crypto-jca" % silhouetteVersion,
  "com.mohiva" %% "play-silhouette-testkit" % silhouetteVersion % "test",
  "org.webjars" %% "webjars-play" % "2.6.3",
  "org.webjars" % "swagger-ui" % swaggerUIVersion,
  "com.adrianhurt" %% "play-bootstrap" % "1.4-P26-B3-SNAPSHOT",
  guice
)


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7"
)

libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.1.0"
)

libraryDependencies ++= Seq{
  "io.swagger" %% "swagger-play2" % "1.6.1-SNAPSHOT"
}

routesGenerator := InjectedRoutesGenerator

routesImport += "utils.route.Binders._"