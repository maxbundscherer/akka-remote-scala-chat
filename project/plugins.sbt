val sbtSCoverageVersion: String       = "1.5.1"
val sbtNativePackagerVersion: String  = "1.2.0"

//SCoverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % sbtSCoverageVersion)

//SBT native packager
addSbtPlugin("com.typesafe.sbt" %% "sbt-native-packager" % sbtNativePackagerVersion)