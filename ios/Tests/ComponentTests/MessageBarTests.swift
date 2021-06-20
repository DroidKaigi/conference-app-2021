import Algorithms
import Component
import SnapshotTesting
import XCTest

final class MessageBarTests: XCTestCase {
    func testMessageBar() {
        let messageBar = MessageBar(title: "DroidKaigi 2021 is coming soon 🎉")
            .frame(width: 280, height: 36)
            .environment(\.colorScheme, .light)
        assertSnapshot(matching: messageBar, as: .image)
    }
}
