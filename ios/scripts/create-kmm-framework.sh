#! /bin/bash

FILE_PATH=`dirname $0`

cd "$FILE_PATH/../../"

./gradlew ios-framework:createXCFramework
