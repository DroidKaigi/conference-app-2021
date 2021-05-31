#! /bin/bash

if [ -n "$PROJECT_DIR" ]; then
    cd "$PROJECT_DIR/../"
fi

shopt -s expand_aliases

alias package-run='xcrun --sdk macosx swift run --package-path Tools'

mkdir -p Sources/Styleguide/Generated
package-run swiftgen
