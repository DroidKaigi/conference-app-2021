import FavoritesFeature
import TestUtils
import XCTest

final class FavoritesFeatureTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testFavoritesScreen() {
        assertPreviewScreenSnapshot(FavoritesScreen_Previews.self)
    }
}
