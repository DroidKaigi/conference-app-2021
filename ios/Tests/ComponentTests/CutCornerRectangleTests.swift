import Component
import TestUtils
import XCTest

final class CutCornerRectangleTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testCutCornerRectangle() {
        assertPreviewSnapshot(CutCornersRectangle_Previews.self)
    }
}
