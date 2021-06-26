import Component
import TestUtils
import XCTest

final class MessageBarTests: XCTestCase {
    func testMessageBar() {
        assertPreviewSnapshot(MessageBar_Previews.self)
    }
}
