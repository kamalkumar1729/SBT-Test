import java.util.Calendar

import sbt.Keys._

import scala.collection.immutable.{List, Map}


val hellokey = taskKey[Int]("Print hello")


val taskkey = Seq(hellokey :=  { println("Outside settings"+Calendar.getInstance().getTime+"end line"); 1729} )


val zerokey = taskKey[Unit]("first key")
zerokey:= {
  println("Zero Key: time is " + Calendar.getInstance().getTime)
  Thread.sleep(3000)
}



val firstkey = taskKey[Unit]("first key")
firstkey:= {
  println("First Key: time is " + Calendar.getInstance().getTime)
  Thread.sleep(1000)
}

// inside or dependent task are evaluated first
// here clean will be evaluated before it starts evaluating printkey.

// second = f(zero,first)
// zero and first executed first, when they BOTH finished , second starts.
val  secondkey = taskKey[Unit]("second key")
secondkey := {
  firstkey.value
  zerokey.value
  println("Second Key: time is "+Calendar.getInstance().getTime)
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


