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

pushd ..
popd
mvn clean install -DskipTests


SERVER_JAR=$SCRIPT_DIR/../server/target/distkv-server-0.1.4-SNAPSHOT-jar-with-dependencies.jar
CLIENT_JAR=$SCRIPT_DIR/../client/target/distkv-client-0.1.4-SNAPSHOT-jar-with-dependencies.jar

SERVER_INSTALLING_DEST=/usr/local/distkv-all/server
CLIENT_INSTALLING_DEST=/usr/local/distkv-all/client

# TODO(qwang): How to avoid these `sudo`?
# Create installation directory.
sudo mkdir -p $SERVER_INSTALLING_DEST
sudo mkdir -p $CLIENT_INSTALLING_DEST

sudo cp $SCRIPT_DIR/run_distkv_server.sh $SERVER_INSTALLING_DEST/distkv-server
sudo chmod +755 $SERVER_INSTALLING_DEST/distkv-server

sudo cp $SCRIPT_DIR/run_distkv_cli.sh $CLIENT_INSTALLING_DEST/distkv-cli
sudo chmod +755 $CLIENT_INSTALLING_DEST/distkv-cli

# create dkv user
sudo useradd -M dkv

sudo cp $SERVER_JAR $SERVER_INSTALLING_DEST/distkv-server-0.1.4-SNAPSHOT-jar-with-dependencies.jar
sudo chown dkv:dkv $SERVER_INSTALLING_DEST/distkv-server-0.1.4-SNAPSHOT-jar-with-dependencies.jar

sudo cp $CLIENT_JAR $CLIENT_INSTALLING_DEST/distkv-client-0.1.4-SNAPSHOT-jar-with-dependencies.jar
sudo chown dkv:dkv $CLIENT_INSTALLING_DEST/distkv-client-0.1.4-SNAPSHOT-jar-with-dependencies.jar

# create soft link to binaries.
sudo ln -s $SERVER_INSTALLING_DEST/distkv-server /usr/local/bin/dkv-server
sudo ln -s $CLIENT_INSTALLING_DEST/distkv-cli /usr/local/bin/dkv-cli

# create dkv folder to store log
sudo mkdir /var/log/dkv
sudo chown dkv:dkv /var/log/dkv

# create dkv folder to store dkv runtime data
sudo mkdir /var/lib/dkv
sudo chown dkv:dkv /var/lib/dkv

echo "Wow~ Dst was installed successfully."
