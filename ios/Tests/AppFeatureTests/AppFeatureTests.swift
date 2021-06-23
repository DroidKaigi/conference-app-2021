import AppFeature
import TestUtils
import XCTest

final class AppFeatureTests: XCTestCase {
    func testAppScreen() {
        assertPreviewSnapshot(AppScreen_Previews.self, with: .iPhoneX)
    }
}
