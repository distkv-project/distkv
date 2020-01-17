#!/usr/bin/env bash

set -e
set -x

sudo rm -rf /usr/local/distkv-all

sudo rm /usr/local/bin/distkv-server
sudo rm /usr/local/bin/distkv-cli

echo "Dst was uninstalled successfully."
