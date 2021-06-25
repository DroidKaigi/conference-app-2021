import AboutFeature
import TestUtils
import XCTest

final class AboutFeatureTests: XCTestCase {
    func testAboutScreen() {
        assertPreviewSnapshot(AboutScreen_Previews.self, with: .iPhoneX)
    }

    func testContributorCell() {
        assertPreviewSnapshot(ContributorCell_Preview.self)
    }

    func testStaffCell() {
        assertPreviewSnapshot(StaffCell_Previews.self)
    }

    func testAboutDroidKaigiScreen() {
        assertPreviewSnapshot(AboutDroidKaigiScreen_Previews.self, with: .iPhoneX)
    }
}
