#!/usr/bin/env bash

set -e
set -x

sudo rm -rf /usr/local/distkv-all

sudo rm /usr/local/bin/dkv-server
sudo rm /usr/local/bin/dkv-cli

echo "Dst was uninstalled successfully."
