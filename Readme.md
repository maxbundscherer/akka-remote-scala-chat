# Akka Remote Scala Chat

[![shields.io](http://img.shields.io/badge/license-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

Author: Maximilian Bundscherer (https://bundscherer-online.de)

**A chat based on akka remote (written in scala)**

## Overview / Features
- Server and client software included
- Create account with `create <USERNAME> <PASSWORD>`
- Login with `login <USERNAME> <PASSWORD>`

## TODO
- **Broadcast** messages to all clients with `<YOURMESSAGE>`
- Write private **volatile messages** to a client with `-p <CLIENTID> <YOURMESSAGE>`
- Write private **permanent messages** to a client with `-s <CLIENTID> <YOURMESSAGE>`
- See all online clients with `-clients`
- See all your permanent messages with `-mymsg`

## Technologies
- Akka
- Akka Remote
- Scala
- ScalaTest
- Slick
- JBCrypt
- sbt-scoverage

## Let's get started
1. See requirements!
2. Import SBT project
3. (Opt) Add SBT task `"run-main mb.MainServer"` (single instance) to your project.
4. (Opt) Add SBT task `"run-main mb.MainClient"` to your project.
5. (Opt) Add SBT task `clean coverage test` to your project.
6. (Opt) Add SBT task `coverageReport` to your project.
7. (Opt) Change logger settings `src/main/resources/application.conf` to `INFO` or `DEBUG`
8. (Opt) Change server address/port `src/main/resources/application.conf` **and** `src/main/mb/actors/client/Supervisor`
9. (Opt) Change client address/port `src/main/resources/application.conf`
10. Start server
11. Start client(s)

### Requirements
- SBT
- Clean MySQL-Database **for run** (see config `src/main/resources/application.conf`)
    - host: `localhost`
    - name: `akka-scala-chat`
    - username: `root`
    - password: ""
- Clean MySQL-Database **for test** (see config `src/test/resources/application.conf`)
    - host: `localhost`
    - name: `akka-scala-chat-test`
    - username: `root`
    - password: ""
    
### Commands
- Run server with `sbt run-main mb.MainServer`
- Run client(s) with `sbt run-main mb.MainClient`
- Test project with `sbt clean coverage test` and generate coverage reports with `sbt coverageReport` (**important: start with clean test-db**)
