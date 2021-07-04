import Component
import TestUtils
import XCTest

final class FeedContentListViewTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }

    func testFeedContentListView() {
        assertPreviewSnapshot(FeedContentListView_Previews.self)
    }
}
