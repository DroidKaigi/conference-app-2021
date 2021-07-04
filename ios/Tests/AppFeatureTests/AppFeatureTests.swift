import AppFeature
import TestUtils
import XCTest

final class AppFeatureTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testAppScreen() {
        assertPreviewScreenSnapshot(AppScreen_Previews.self)
    }
}
