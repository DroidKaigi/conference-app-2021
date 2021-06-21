import Component
import TestUtils
import XCTest

final class CardTests: XCTestCase {
    func testLargeCard() {
        assertPreviewSnapshot(LargeCard_Previews.self)
    }

    func testMediumCard() {
        assertPreviewSnapshot(MediumCard_Previews.self)
    }

    func testSmallCard() {
        assertPreviewSnapshot(SmallCard_Previews.self)
    }
}
