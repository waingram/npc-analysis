#!/usr/bin/env bash
set -ex

mv /tmp/pom.xml .
mvn clean compile assembly:single

# This is the master script for the capsule. When you click "Reproducible Run", the code in this file will execute.
java -jar target/neural-parscit-evaluation-1.0-SNAPSHOT-jar-with-dependencies.jar
