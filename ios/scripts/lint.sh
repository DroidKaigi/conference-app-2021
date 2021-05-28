#! /bin/bash

SHELL_PATH=`pwd -P`
cd "$SHELL_PATH/../"

xcrun --sdk macosx mint run swiftlint --fix
xcrun --sdk macosx mint run swiftlint

