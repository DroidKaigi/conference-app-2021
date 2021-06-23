import MediaFeature
import TestUtils
import XCTest

final class MediaFeatureTests: XCTestCase {
    func testMediaDetail() {
        assertPreviewSnapshot(MediaDetail_Previews.self, with: .iPhoneX)
    }

    func testMediaListView() {
        assertPreviewSnapshot(MediaListView_Previews.self, with: .iPhoneX)
    }

    func testMediaScreen() {
        assertPreviewSnapshot(MediaScreen_Previews.self, with: .iPhoneX)
    }

    func testMediaSection() {
        assertPreviewSnapshot(MediaSection_Previews.self)
    }

    func testMediaSectionHeader() {
        assertPreviewSnapshot(MediaSectionHeader_Previews.self)
    }

    func testSearchResultView() {
        assertPreviewSnapshot(SearchResultView_Previews.self, with: .iPhoneX)
    }
}
