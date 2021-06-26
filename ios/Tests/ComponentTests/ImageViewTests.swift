import Component
import TestUtils
import XCTest

final class ImageViewTests: XCTestCase {
    func testImageView() {
        assertPreviewSnapshot(ImageView_Previews.self)
    }
}
