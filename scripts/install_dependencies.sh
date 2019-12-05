#!/usr/bin/env bash

# This script installs the unresolved dependencies.

set -e
set -x

# Install drpc.
DRPC_REPOSITORY_URL=https://github.com/dst-project/drpc.git
DRPC_COMMIT_ID=16af51c2c092e7326080adbc7df2dce8a473b5d4

git clone ${DRPC_REPOSITORY_URL} drpc_tmp
pushd drpc_tmp
git checkout $DRPC_COMMIT_ID
mvn clean install -DskipTests
popd

echo "Drpc was installed successfully."

echo "All dependencies were installed successfully."
