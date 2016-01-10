enablePlugins(JavaAppPackaging)

name         := "akka-game-backen-part1"
organization := "io.github.chumper"
version      := "1.0"
scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.1"
  val akkaStreamV = "2.0.1"
  val scalaTestV  = "2.2.6"
  val scaldiV     = "0.5.7"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-cluster-sharding"                % akkaV,
    "com.typesafe.akka" %% "akka-cluster"                         % akkaV,
    "com.typesafe.akka" %% "akka-persistence"                     % akkaV,
    "com.typesafe.akka" %% "akka-distributed-data-experimental"   % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamV,
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test",
    "org.scaldi"        %% "scaldi-akka"                          % scaldiV,
    "io.jsonwebtoken"   % "jjwt"                                  % "0.6.0",

    // level db for local persistence
    "org.iq80.leveldb"            % "leveldb"          % "0.7",
    "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"
  )
}

Revolver.settings


fork in run := true
