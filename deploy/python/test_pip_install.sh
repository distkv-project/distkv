#!/usr/bin/env bash

set -x
# Cause the script to exit if a single command fails.
set -e

# Build dkv-server and `dkv-cli` binary.
pip install -e . -v

# Start `dkv server`
nohup dkv-server >> /dev/null 2>&1 &

# Start `dkv-cli` and test
result=$(echo "str.put k1 v1 \n str.get k1 \n exit" | dkv-cli)

# Do clean up
sleep 1
ps -ef | grep dkv-server | awk '{print $2}' | xargs kill -9
