name := "akka-remote-scala-chat"
version := "1.0"
scalaVersion := "2.12.1"

val akkaVersion: String       = "2.5.4"
val scalaTestVersion: String  = "3.0.1"
val slickVersion : String     = "3.2.1"
val mySqlVersion : String     = "6.0.6"

//Akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion
)

//ScalaTest
libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

//Slick
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
)

//MySql connector
libraryDependencies += "mysql" % "mysql-connector-java" % mySqlVersion

//JBCrypt
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"

//SLF4J Logger (for slick) - disabled - enable if you want slick to use a real logger
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"