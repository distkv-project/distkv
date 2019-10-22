#!/usr/bin/env bash

set -e
set -x

sudo rm -rf /usr/local/dst-all

sudo rm /usr/local/bin/dst-server
sudo rm /usr/local/bin/dst-cli

echo "Dst was uninstalled successfully."
