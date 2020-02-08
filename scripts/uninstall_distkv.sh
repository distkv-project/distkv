#!/usr/bin/env bash

set -e
set -x

DKV_PID_FILe=/var/lib/dkv/dkv-server.currentpid
if [ -f "$DKV_PID_FILe" ]; then
  DKV_PID=$(cat "$DKV_PID_FILe")
fi

if [ -n "$DKV_PID" ] && sudo kill -0 "$DKV_PID" > /dev/null 2>&1; then
  echo "you can't uninstall it because of the dkv server is running"
  exit 1;
fi

sudo rm -rf /usr/local/distkv-all

sudo rm /usr/local/bin/dkv-server
sudo rm /usr/local/bin/dkv-cli
sudo rm -rf /var/log/dkv
sudo rm -rf /var/lib/dkv
sudo userdel dkv

echo "Dst was uninstalled successfully."
