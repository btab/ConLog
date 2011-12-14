// Project

name := "conlog"

organization := "com.bluetheta"

version := "1.0"


// SBT

scalaSource in Compile <<= baseDirectory / "src"

scalaSource in Test <<= baseDirectory / "specs"

publishTo := Some(Resolver.file("Git Repo", file("repo")))


// Scala

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-deprecation", "-unchecked")


// Managed libraries

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)
