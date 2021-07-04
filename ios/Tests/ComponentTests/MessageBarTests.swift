import Component
import TestUtils
import XCTest

final class MessageBarTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testMessageBar() {
        assertPreviewSnapshot(MessageBar_Previews.self)
    }
}
