SWIFT_RUN=swift run -c release --package-path Tools
WORKSPACE=DroidKaigi2021.xcworkspace
PLATFORM_IOS =iOS Simulator,name=iPhone 12 Pro,OS=14.4

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
	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme DroidKaigiMPP \
		-configuration Debug \
		-destination platform="$(PLATFORM_IOS)"

.PHONE: build-kmm-module-release
build-kmm-module-release:
	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme DroidKaigiMPP \
		-configuration Release \
		-destination platform="$(PLATFORM_IOS)"

# Test App Debug
.PHONY: test-app-debug
test-app-debug:
	make build-kmm-module-debug

	xcodebuild test \
		-workspace $(WORKSPACE) \
		-scheme DroidKaigi\ 2021 \
		-configuration Debug \
		-destination platform="$(PLATFORM_IOS)"

# Test App Release
.PHONY: test-app-release
test-app-release:
	make build-kmm-module-release

	xcodebuild test \
		-workspace $(WORKSPACE) \
		-scheme DroidKaigi\ 2021 \
		-configuration Release \
		-destination platform="$(PLATFORM_IOS)"

# Build App Debug
.PHONY: build-app-debug
build-app-debug:
	make build-kmm-module-debug

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme DroidKaigi\ 2021 \
		-configuration Debug \
		-destination platform="$(PLATFORM_IOS)"

# Build App Release
.PHONY: build-app-release
build-app-release:
	make build-kmm-module-release

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme DroidKaigi\ 2021 \
		-configuration Release \
		-destination platform="$(PLATFORM_IOS)"

# Build Modules
.PHONY: build-modules
build-modules:
	make build-kmm-module-debug

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme AboutFeature \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme AppFeature \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme Component \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme FavoritesFeature \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme HomeFeature \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme MediaFeature \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme Model \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme Repository \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme Styleguide \
		-destination platform="$(PLATFORM_IOS)"

	xcodebuild build \
		-workspace $(WORKSPACE) \
		-scheme Utility \
		-destination platform="$(PLATFORM_IOS)"
