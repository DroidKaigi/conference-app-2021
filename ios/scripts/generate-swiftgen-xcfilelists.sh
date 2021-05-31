#! /bin/bash

XCFILELIST_OUTPUT=XCFileLists
INPUT_FILE_NAME=SwiftGenInput.xcfilelist
OUTPUT_FILE_NAME=SwiftGenOutput.xcfilelist

shopt -s expand_aliases

alias package-run='xcrun --sdk macosx swift run --package-path Tools'

echo "Generating SwiftGen Input files and Output files..."
mkdir -p $XCFILELIST_OUTPUT
package-run swiftgen config generate-xcfilelists \
    --inputs "$XCFILELIST_OUTPUT/$INPUT_FILE_NAME" \
    --outputs "$XCFILELIST_OUTPUT/$OUTPUT_FILE_NAME"
