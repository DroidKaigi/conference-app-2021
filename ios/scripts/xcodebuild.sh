#!/bin/bash

TYPE=${1}
SCHEME=${2}
CONFIGURATION=${3}

WORKSPACE=DroidKaigi2021.xcworkspace
PLATFORM_IOS="iOS Simulator,name=iPhone 12 Pro,OS=14.4"

echo "⚙️  Building $SCHEME..."
set -o pipefail && xcodebuild $TYPE \
-workspace $WORKSPACE \
-scheme "$SCHEME" \
-configuration $CONFIGURATION \
-destination platform="$PLATFORM_IOS" | ./scripts/swift.sh xcbeautify --quiet
