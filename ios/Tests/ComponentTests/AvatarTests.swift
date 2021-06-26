import Component
import TestUtils
import XCTest

final class AvatarTests: XCTestCase {
    func testAvatarView() {
        assertPreviewSnapshot(AvatarView_Previews.self)
    }
}
