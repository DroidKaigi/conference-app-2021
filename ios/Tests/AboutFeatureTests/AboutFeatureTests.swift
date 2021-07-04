import AboutFeature
import TestUtils
import XCTest

final class AboutFeatureTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testAboutScreen() {
        assertPreviewScreenSnapshot(AboutScreen_Previews.self)
    }

    func testContributorCell() {
        assertPreviewSnapshot(ContributorCell_Preview.self)
    }

    func testStaffCell() {
        assertPreviewSnapshot(StaffCell_Previews.self)
    }

    func testAboutDroidKaigiScreen() {
        assertPreviewScreenSnapshot(AboutDroidKaigiScreen_Previews.self)
    }
}
