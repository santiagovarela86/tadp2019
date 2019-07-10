name := "scala"

version := "1"

organization := "edu.ar.utn.tadp"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "junit" % "junit" % "4.11" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)

libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test->default"


EclipseKeys.withSource := true
EclipseKeys.withJavadoc := true
