#!/usr/bin/env bash

unamestr="$(uname)"
if [[ "$unamestr" == "Linux" ]]; then
    SCRIPT_ABS=$(readlink -f "$0")
elif [[ "$unamestr" == "Darwin" ]]; then
    SCRIPT_ABS=$(greadlink -f "$0")
else
    echo "Not support the platform:$unamestr"
    exit 1
fi

SCRIPT_DIR=$(dirname $SCRIPT_ABS)

java -classpath $SCRIPT_DIR/dst-server-0.1.0-SNAPSHOT-jar-with-dependencies.jar org.dst.server.service.DstRpcServer
