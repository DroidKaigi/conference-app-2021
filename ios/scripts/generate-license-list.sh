#!/bin/bash

set -e

cd "$(dirname "$0")/.."

xcrun --sdk macosx swift run -c release --package-path Tools license-plist \
  --package-path DroidKaigi2021.xcworkspace/xcshareddata/swiftpm/Package.resolved \
  --output-path DroidKaigi\ 2021/DroidKaigi\ 2021/Settings.bundle \
  --add-version-numbers \
  --suppress-opening-directory
