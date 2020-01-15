#!/usr/bin/env bash

set -x
# Cause the script to exit if a single command fails.
set -e

# install
pushd ../
mvn clean install -DskipTests

# copy dst-server jar to wheel dist.
cp server/target/dst-server-0.1.3-SNAPSHOT-jar-with-dependencies.jar python/dst/jars/dst-server.jar
# copy dst-cli jar to wheel dist.
cp client/target/dst-client-0.1.3-SNAPSHOT-jar-with-dependencies.jar python/dst/jars/dst-cli.jar

popd
