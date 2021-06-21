import Component
import TestUtils
import XCTest

final class ListTests: XCTestCase {
    func testListItem() {
        assertPreviewSnapshot(ListItem_Previews.self)
    }
}
