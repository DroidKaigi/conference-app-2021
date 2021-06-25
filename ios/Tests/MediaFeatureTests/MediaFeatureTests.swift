import MediaFeature
import TestUtils
import XCTest

final class MediaFeatureTests: XCTestCase {
    func testMediaDetail() {
        assertPreviewScreenSnapshot(MediaDetail_Previews.self)
    }

    func testMediaListView() {
        assertPreviewScreenSnapshot(MediaListView_Previews.self)
    }

    func testMediaScreen() {
        assertPreviewScreenSnapshot(MediaScreen_Previews.self)
    }

    func testMediaSection() {
        assertPreviewSnapshot(MediaSection_Previews.self)
    }

    func testMediaSectionHeader() {
        assertPreviewSnapshot(MediaSectionHeader_Previews.self)
    }

    func testSearchResultView() {
        assertPreviewScreenSnapshot(SearchResultView_Previews.self)
    }
}
