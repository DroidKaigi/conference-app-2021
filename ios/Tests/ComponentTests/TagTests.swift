import Component
import Model
import SnapshotTesting
import XCTest

final class TagTests: XCTestCase {
    func testTag() {
        Media.allCases.forEach { media in
            let tag = Tag(type: media, tapAction: {})
                .frame(width: 103, height: 24)
            assertSnapshot(matching: tag, as: .image)
        }
    }
}
