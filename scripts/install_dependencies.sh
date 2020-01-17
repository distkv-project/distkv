#!/usr/bin/env bash

# This script installs the unresolved dependencies.

set -e
set -x

# Install drpc.
DRPC_REPOSITORY_URL=https://github.com/distkv-project/drpc.git
DRPC_COMMIT_ID=91118d370ff86a7be05d5ecb61236d5202f7d837

git clone ${DRPC_REPOSITORY_URL} drpc_tmp
pushd drpc_tmp
git checkout $DRPC_COMMIT_ID
mvn clean install -DskipTests
popd
rm -rf drpc_tmp

echo "Drpc was installed successfully."

echo "All dependencies were installed successfully."
