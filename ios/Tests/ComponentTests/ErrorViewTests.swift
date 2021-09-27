import Component
import TestUtils
import XCTest

final class ErrorViewTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }

    func testErrorView() {
        assertPreviewSnapshot(ErrorView_Previews.self)
    }
}
