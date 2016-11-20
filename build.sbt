name := """property-based-testing"""

version := "1.0"

scalaVersion := "2.12.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test" withSources()
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test" withSources()