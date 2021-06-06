#! /bin/bash

SHELL_PATH=`pwd -P`

cd "$SHELL_PATH/../../"

./gradlew :ios-framework:packForXCode -PXCODE_CONFIGURATION=${CONFIGURATION}
