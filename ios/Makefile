SWIFT_RUN=swift run -c release --package-path Tools
WORKSPACE=DroidKaigiFeeder.xcworkspace

.PHONY: open
open:
	@open $(WORKSPACE)

# SwiftLint
.PHONY: run-swiftlint
run-swiftlint:
	$(SWIFT_RUN) swiftlint

# SwiftGen
.PHONY: run-swiftgen
run-swiftgen:
	./scripts/swiftgen.sh

.PHONY: run-swiftgen-generate-xcfilelists
run-swiftgen-generate-xcfilelists:
	./scripts/generate-swiftgen-xcfilelists.sh

# KMM Module
.PHONY: create-kmm-framework
create-kmm-framework:
	./scripts/create-kmm-framework.sh

.PHONE: build-kmm-module-debug
build-kmm-module-debug:
	./scripts/xcodebuild.sh build DroidKaigiMPP Debug

.PHONE: build-kmm-module-release
build-kmm-module-release:
	./scripts/xcodebuild.sh build DroidKaigiMPP Release

# Test App Debug
.PHONY: test-app-debug
test-app-debug:
	make build-kmm-module-debug
	./scripts/xcodebuild.sh test "DroidKaigi Feeder" Debug

# Test App Release
.PHONY: test-app-release
test-app-release:
	make build-kmm-module-release
	./scripts/xcodebuild.sh test "DroidKaigi Feeder" Release

# Build App Debug
.PHONY: build-app-debug
build-app-debug:
	make build-kmm-module-debug
	./scripts/xcodebuild.sh build "DroidKaigi Feeder" Debug

# Build App Release
.PHONY: build-app-release
build-app-release:
	make build-kmm-module-release
	./scripts/xcodebuild.sh build "DroidKaigi Feeder" Release

# Build Modules
.PHONY: build-modules
build-modules:
	make build-kmm-module-debug
	./scripts/xcodebuild.sh build AboutFeature Debug
	./scripts/xcodebuild.sh build AppFeature Debug
	./scripts/xcodebuild.sh build Component Debug
	./scripts/xcodebuild.sh build FavoritesFeature Debug
	./scripts/xcodebuild.sh build HomeFeature Debug
	./scripts/xcodebuild.sh build MediaFeature Debug
	./scripts/xcodebuild.sh build Model Debug
	./scripts/xcodebuild.sh build Repository Debug
	./scripts/xcodebuild.sh build Styleguide Debug
	./scripts/xcodebuild.sh build Utility Debug

# Test for Modules
.PHONY: test-modules
test-modules:
	make build-kmm-module-debug
	./scripts/xcodebuild.sh test ModuleTest Debug

# Take Snapshots
.PHONY: take-snapshots
take-snapshots:
	make build-kmm-module-debug
	./scripts/xcodebuild.sh test ModuleTestRecording Debug || echo
