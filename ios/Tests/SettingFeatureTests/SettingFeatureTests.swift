import SettingFeature
import TestUtils
import XCTest

final class SettingFeatureTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testSettingScreen() {
        assertPreviewScreenSnapshot(SettingScreen_Previews.self)
    }

    func testSettingToggleItem() {
        assertPreviewSnapshot(SettingToggleItem_Previews.self)
    }
}
