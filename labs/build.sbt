name := "lab_1"

version := "0.1"

scalaVersion := "2.12.10"

lazy val commonSettings = Seq(
  scalaVersion := "2.12.10",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  )
)

lazy val `stage2-3` = project
  .in(file("stage2-3"))
  .settings(
    commonSettings
  )

lazy val stage4 = project
  .in(file("stage4"))
  .settings(
    commonSettings
  )

lazy val root = project
  .in(file("."))
  .aggregate(`stage2-3`, `stage4`)
