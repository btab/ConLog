// Project

name := "Conlog"

organization := "BlueTheta"

version := "1.0"


// SBT

scalaSource in Compile <<= baseDirectory / "src"

scalaSource in Test <<= baseDirectory / "specs"

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)


// Scala

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-deprecation", "-unchecked")


// Managed libraries

//resolvers ++= Seq()

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)
