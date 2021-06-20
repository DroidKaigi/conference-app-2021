import Component
import SnapshotTesting
import XCTest

final class AvatarTests: XCTestCase {
    func testAvatarView() {
        let largeAvatarView = AvatarView(avatarImageURL: nil, style: .large)
        let smallAvatarView = AvatarView(avatarImageURL: nil, style: .small)

        assertSnapshot(matching: largeAvatarView, as: .image)
        assertSnapshot(matching: smallAvatarView, as: .image)
    }
}
