# Akka Remote Scala Chat

[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)](#)
[![shields.io](http://img.shields.io/badge/license-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

**A chat based on akka remote (written in scala)**

Author: Maximilian Bundscherer (https://bundscherer-online.de)

Test-Coverage: **84.96%**

## Overview / Features
- Server and client software included
- Docker local publish included (for server)
- Create account with ``create <USERNAME> <PASSWORD>``
- Login with ``login <USERNAME> <PASSWORD>``
- **Broadcast messages** to all online clients with ``<YOURMESSAGE>``
- Write **private messages** with ``-p <USERNAME> <YOURMESSAGE>``
- Logout with ``-logout``

### Used technologies
- Akka: *server and client base*
- Akka Remote: *network communication*
- Docker: *Container*
- Scala: *programming language*
- ScalaTest: *testing project*
- Slick: *database connection*
- JBCrypt: *hashing user password*
- sbt-scoverage: *generate test coverage report(s)*

## Let's get started
1. See requirements!
2. Import SBT project
3. (Opt) Add SBT task ``"run-main com.mb.akkaremoteChat.MainServer"`` (single instance) to your project.
4. (Opt) Add SBT task ``"run-main com.mb.akkaremoteChat.MainClient"`` to your project.
5. (Opt) Add SBT task ``clean coverage test`` to your project.
6. (Opt) Add SBT task ``coverageReport`` to your project.
7. (Opt) Change logger settings ``src/scala/main/resources/application.conf`` to `INFO` or `DEBUG`
8. (Opt) Change server address/port ``src/scala/main/resources/application.conf`` **and** ``src/scala/main/com/mb/akkaremotechat/actors/client/Supervisor``
9. (Opt) Change client address/port ``src/scala/main/resources/application.conf``
10. Start server
11. Start client(s)

### Requirements
- SBT
- Clean MySQL-Database **for run** (see config ``src/scala/main/resources/application.conf``)
    - host: ``localhost``
    - name: ``akka-scala-chat``
    - username: ``root``
    - password: ""
- Clean MySQL-Database **for test** (see config ``src/scala/test/resources/application.conf``)
    - host: ``localhost``
    - name: ``akka-scala-chat-test``
    - username: ``root``
    - password: ""
    
### Commands
- Run server with ``sbt run-main com.mb.akkaremoteChat.MainServer``
- Run client(s) with ``sbt run-main com.mb.akkaremoteChat.MainClient``

### Docker local publish
1. (Opt) Change ports in ``build.sbt``
2. (Opt) Change main class in ``build.sbt``
3. Run command ``sbt docker:publishLocal``
4. Run image ``docker run --name akka-remote-scala-chat -p 5150:5150 <IMAGE-ID>``

### Test project with coverage report(s)
1. Setup clean test database (see requirements)
2. Run command ``sbt clean coverage test``
3. Run command ``sbt coverageReport``
