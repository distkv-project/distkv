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

java -classpath $SCRIPT_DIR/distkv-client-0.1.3-jar-with-dependencies.jar com.distkv.client.commandlinetool.DistkvCommandLineToolStarter
