import AppFeature
import TestUtils
import XCTest

final class AppFeatureTests: XCTestCase {
    func testAppScreen() {
        assertPreviewScreenSnapshot(AppScreen_Previews.self)
    }
}
