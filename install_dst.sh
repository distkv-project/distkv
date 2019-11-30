#!/usr/bin/env bash

set -e
set -x

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

mvn clean install -DskipTests

SERVER_JAR=$SCRIPT_DIR/server/target/dst-server-0.1.1-jar-with-dependencies.jar
CLIENT_JAR=$SCRIPT_DIR/client/target/dst-client-0.1.1-jar-with-dependencies.jar

SERVER_INSTALLING_DEST=/usr/local/dst-all/server
CLIENT_INSTALLING_DEST=/usr/local/dst-all/client

# TODO(qwang): How to avoid these `sudo`?
# Create installation directory.
sudo mkdir -p $SERVER_INSTALLING_DEST
sudo mkdir -p $CLIENT_INSTALLING_DEST

sudo cp $SCRIPT_DIR/scripts/run_dst_server.sh $SERVER_INSTALLING_DEST/dst-server
sudo cp $SCRIPT_DIR/scripts/run_dst_cli.sh $CLIENT_INSTALLING_DEST/dst-cli

sudo cp $SERVER_JAR $SERVER_INSTALLING_DEST/dst-server-0.1.1-jar-with-dependencies.jar
sudo cp $CLIENT_JAR $CLIENT_INSTALLING_DEST/dst-client-0.1.1-jar-with-dependencies.jar

# create soft link to binaries.
ln -s $SERVER_INSTALLING_DEST/dst-server /usr/local/bin/dst-server
ln -s $CLIENT_INSTALLING_DEST/dst-cli /usr/local/bin/dst-cli

echo "Wow~ Dst was installed successfully."
