import SettingFeature
import TestUtils
import XCTest

final class SettingFeatureTests: XCTestCase {
    func testSettingScreen() {
        assertPreviewScreenSnapshot(SettingScreen_Previews.self)
    }

    func testSettingToggleItem() {
        assertPreviewSnapshot(SettingToggleItem_Previews.self)
    }
}
