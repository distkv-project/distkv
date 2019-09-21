#!/usr/bin/env bash

set -e

# Clean up caches
rm -rf build

# Build all java packages
mvn clean install -DskipTests

# Make sure that we are under project folder
mkdir -p build/temp
pushd build/temp

cmake ../../deploy/launcher
make -j 4
popd

# Copy the packages to dest dir.
mkdir -p build/dst-server
mkdir -p build/dst-cli

cp ./server/target/dst-server-0.1.0-SNAPSHOT-jar-with-dependencies.jar ./build/dst-server
cp build/temp/dst-server ./build/dst-server

cp ./client/target/dst-client-0.1.0-SNAPSHOT-jar-with-dependencies.jar ./build/dst-cli
cp build/temp/dst-cli ./build/dst-cli

