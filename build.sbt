import java.util.Calendar

import sbt.Keys._

import scala.collection.immutable.{List, Map}


val hellokey = taskKey[Int]("Print hello")
//hellokey :=  println("Outside settings"+Calendar.getInstance().getTime+"end line")



name := { println(" time at name init is "+ Calendar.getInstance.getTime);  "SBT_test" }

version := "0.1"

scalaVersion := "2.12.6"

val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.1"
val playJson = "com.typesafe.play" %% "play-json" % "2.6.9"


lazy val helloCore = (project in file("HelloCore"))
  .settings(
    name := { println("hello core name initialization"); "Hello Core" },
    libraryDependencies ++= Seq(gigahorse, playJson),
    libraryDependencies += scalaTest % Test,
  )

lazy val hello = (project in file("."))
  .aggregate(helloCore)
  .dependsOn(helloCore)
  .settings(
    hellokey := { println("Inside settings "+Calendar.getInstance().getTime+"end line"); 1729},
    name := "HelloSbt",
    libraryDependencies += scalaTest % Test,
  )


