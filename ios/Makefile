SWIFT_RUN=swift run -c release --package-path Tools
WORKSPACE=DroidKaigi2021.xcworkspace

.PHONY: open
open:
	@open $(WORKSPACE)

.PHONY: run-swiftlint
run-swiftlint:
	$(SWIFT_RUN) swiftlint
