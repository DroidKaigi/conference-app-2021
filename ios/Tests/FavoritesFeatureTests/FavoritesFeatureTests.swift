import FavoritesFeature
import TestUtils
import XCTest

final class FavoritesFeatureTests: XCTestCase {
    func testFavoritesScreen() {
        assertPreviewSnapshot(FavoritesScreen_Previews.self, with: .iPhoneX)
    }
}
