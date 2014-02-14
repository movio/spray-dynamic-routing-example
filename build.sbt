
scalaVersion := "2.10.3"

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "spray repo" at "http://repo.spray.io"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.4",
  "io.spray" % "spray-routing" % "1.2.1",
  "com.typesafe.akka" %% "akka-testkit" % "2.2.4" % "test",
  "io.spray" % "spray-testkit" % "1.2.1" % "test",
  "org.scalatest" %% "scalatest" % "2.0" % "test"
)
