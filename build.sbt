name := "DM"
scalaVersion := "2.11.1"
version := "0.0.1-SNAPSHOT"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.0.0"
libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.0.0" % "test"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-client" % "2.7.3"
)

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

