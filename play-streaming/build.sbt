import play.PlayImport.PlayKeys._
import play.twirl.sbt.Import.TwirlKeys._

name := "http_streaming"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

templateFormats := templateFormats.value + ("pagelet" -> "util.PageletFormat")

templateImports += "util.Pagelet"

