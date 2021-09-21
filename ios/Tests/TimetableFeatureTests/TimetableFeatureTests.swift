import TestUtils
import TimetableFeature
import XCTest

final class TimetableFeatureTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }
    
    func testTimetableScreen() {
        assertPreviewScreenSnapshot(TimetableScreen_Previews.self)
    }
    
    func testTimetableContent() {
        assertPreviewScreenSnapshot(TimetableContent_Previews.self)
    }
    
    func testTimetableDetailScreen() {
        assertPreviewScreenSnapshot(TimetableDetailScreen_Previews.self)
    }
}
