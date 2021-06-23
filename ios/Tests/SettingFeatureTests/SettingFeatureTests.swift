import SettingFeature
import TestUtils
import XCTest

final class SettingFeatureTests: XCTestCase {
    func testSettingScreen() {
        assertPreviewSnapshot(SettingScreen_Previews.self, with: .iPhoneX)
    }

    func testSettingToggleItem() {
        assertPreviewSnapshot(SettingToggleItem_Previews.self)
    }
}
