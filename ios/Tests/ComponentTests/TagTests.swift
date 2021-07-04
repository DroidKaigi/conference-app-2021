import Component
import TestUtils
import XCTest

final class TagTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testTag() {
        assertPreviewSnapshot(Tag_Previews.self)
    }
}
