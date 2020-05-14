#!/usr/bin/env bash

set -x
# Cause the script to exit if a single command fails.
set -e

# Build cpp part.

mkdir -p cpp/build
pushd cpp/build
cmake ..
make -j 4
popd

# Copy libjava_storage_engine.dylib to storageengine module.
mkdir -p storageengine/native_dependencies
# Copy libjava_storage_engine.dylib or libjava_storage_engine.so
cp cpp/build/libjava_storage_engine.* storageengine/native_dependencies/

# mvn install
mvn clean install -DskipTests
