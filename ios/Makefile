SWIFT_RUN=swift run -c release --package-path Tools
WORKSPACE=DroidKaigi2021.xcworkspace

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
