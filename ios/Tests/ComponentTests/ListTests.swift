import Component
import TestUtils
import XCTest

final class ListTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testListItem() {
        assertPreviewSnapshot(ListItem_Previews.self)
    }
}
