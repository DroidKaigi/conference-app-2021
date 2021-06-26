import FavoritesFeature
import TestUtils
import XCTest

final class FavoritesFeatureTests: XCTestCase {
    func testFavoritesScreen() {
        assertPreviewScreenSnapshot(FavoritesScreen_Previews.self)
    }
}
