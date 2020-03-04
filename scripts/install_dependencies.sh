#!/usr/bin/env bash

# This script installs the unresolved dependencies.

set -e
set -x

# Install dousi RPC.
DOUSI_REPOSITORY_URL=https://github.com/jovany-wang/dousi.git
DOUSI_COMMIT_ID=23e7c544d36979e95bb40f7efe54b53b6a34e83b

git clone ${DOUSI_REPOSITORY_URL} dousi_tmp
pushd dousi_tmp
git checkout ${DOUSI_COMMIT_ID}
mvn clean install -DskipTests
popd
rm -rf dousi_tmp

echo "Dousi RPC was installed successfully."

echo "All dependencies were installed successfully."
