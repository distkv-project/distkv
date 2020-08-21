#!/usr/bin/env bash

# This script installs the unresolved dependencies.

set -e
set -x

# Install dousi RPC.
DOUSI_REPOSITORY_URL=https://github.com/distkv-project/dousi-rpc.git
DOUSI_COMMIT_ID=3cdabb087cd5d6c58ed2d9f4128add96074c8aec

git clone ${DOUSI_REPOSITORY_URL} dousi_tmp
pushd dousi_tmp
git checkout ${DOUSI_COMMIT_ID}
mvn clean install -DskipTests
popd
rm -rf dousi_tmp

echo "Dousi RPC was installed successfully."

echo "All dependencies were installed successfully."
