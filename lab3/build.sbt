name := "lab3"

version := "0.1"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.1",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  )
)

lazy val stage2 = project
  .in(file("stage2"))
  .settings(
    commonSettings
  )

lazy val stage3 = project
  .in(file("stage3"))
  .settings(
    commonSettings
  )
