#! /bin/bash

SHELL_PATH=`pwd -P`
cd "$SHELL_PATH/../"

shopt -s expand_aliases

alias package-run='xcrun --sdk macosx swift run --package-path Tools'

CHANGED_FILES=$(git status -s | grep -E '^\s*[^D]+\s+.*\.swift$' | awk -F ' ' '{print $2}')

if [ -z $CHANGED_FILES ]; then
    echo "No files changed. Skip prebuild script."
    exit 0
fi

echo execute SwiftLint
echo $CHANGED_FILES | while read filename; do
    package-run swiftlint --fix --path $filename
    package-run swiftlint --path $filename
done
