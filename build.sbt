import java.util.Calendar

import sbt.Keys._

import scala.collection.immutable.{List, Map}


val hellokey = taskKey[Int]("Print hello")


val taskkey = Seq(hellokey :=  { println("Outside settings"+Calendar.getInstance().getTime+"end line"); 1729} )



// inside or dependent task are evaluated first
// here clean will be evaluated before it starts evaluating printkey.
val  printkey = taskKey[Unit]("")
printkey := {
  clean.value
  println("hello from print task.")
}



name := { println(" time at name init is "+ Calendar.getInstance.getTime);  "SBT_test" }

version := "0.1"

scalaVersion := "2.12.6"

val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.1"
val playJson = "com.typesafe.play" %% "play-json" % "2.6.9"


lazy val helloCore = (project in file("HelloCore"))
  .settings(
    libraryDependencies ++= Seq(gigahorse, playJson),
    libraryDependencies += scalaTest % Test,
  )

lazy val hello = (project in file("."))
  .aggregate(helloCore)
  .dependsOn(helloCore)
  .settings(
    taskkey,
    name := "HelloSbt",
    libraryDependencies += scalaTest % Test,
  )


