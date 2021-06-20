#!/bin/bash

PACKAGE_NAME=${1?"USAGE: swift_run.sh #{package name} #{parameter}"}
PARAMETERS=${@:2}
PACKAGE_DIR=$(swift build -c release --show-bin-path --package-path Tools)
PACKAGE=$PACKAGE_DIR/$PACKAGE_NAME

if [ ! -e $PACKAGE ]; then
  echo "$PACKAGE_NAME has not been installed"
  swift build -c release --product $PACKAGE_NAME
  echo "Build succeeded"
else
  echo "$PACKAGE_NAME already installed"
fi

$PACKAGE $PARAMETERS
