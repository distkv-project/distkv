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
DKV_SERVER_JAR="${SCRIPT_DIR}/distkv-server-0.1.3-SNAPSHOT-jar-with-dependencies.jar"
DKV_SERVER_MAIN_CLASS="com.distkv.server.storeserver.StoreServer"

sudo -u dkv bash -c "java -classpath ${DKV_SERVER_JAR} ${DKV_SERVER_MAIN_CLASS} 1> /dev/null 2>&1 & echo \$! > /var/lib/dkv/dkv-server.currentpid"