#!/bin/bash

TYPE=${1}
SCHEME=${2}
CONFIGURATION=${3}

SWIFT_RUN="swift run -c release --package-path Tools"
WORKSPACE=DroidKaigiFeeder.xcworkspace
PLATFORM_IOS="iOS Simulator,name=iPhone 12 Pro,OS=15.0"

echo "⚙️  Building $SCHEME..."
set -o pipefail && xcodebuild $TYPE \
-workspace $WORKSPACE \
-scheme "$SCHEME" \
-configuration $CONFIGURATION \
-destination platform="$PLATFORM_IOS" | $SWIFT_RUN xcbeautify --quiet
