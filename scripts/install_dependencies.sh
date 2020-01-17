#!/usr/bin/env bash

# This script installs the unresolved dependencies.

set -e
set -x

# Install drpc.
DRPC_REPOSITORY_URL=https://github.com/distkv-project/drpc.git
DRPC_COMMIT_ID=4db43a029d4c2cfb9750f87a17754f76435648e0

git clone ${DRPC_REPOSITORY_URL} drpc_tmp
pushd drpc_tmp
git checkout $DRPC_COMMIT_ID
mvn clean install -DskipTests
popd
rm -rf drpc_tmp

echo "Drpc was installed successfully."

echo "All dependencies were installed successfully."
