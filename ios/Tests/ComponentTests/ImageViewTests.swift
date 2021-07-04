import Component
import TestUtils
import XCTest

final class ImageViewTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testImageView() {
        assertPreviewSnapshot(ImageView_Previews.self)
    }
}
