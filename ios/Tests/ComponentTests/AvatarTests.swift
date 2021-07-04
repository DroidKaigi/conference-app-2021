import Component
import TestUtils
import XCTest

final class AvatarTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testAvatarView() {
        assertPreviewSnapshot(AvatarView_Previews.self)
    }
}
