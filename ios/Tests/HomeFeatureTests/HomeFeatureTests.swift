import HomeFeature
import TestUtils
import XCTest

final class HomeFeatureTests: XCTestCase {
    override func setUp() {
        initSnapshotTesting()
    }

    func testHomeScreen() {
        assertPreviewScreenSnapshot(HomeScreen_Previews.self)
    }

    func testQuestionnaireView() {
        assertPreviewSnapshot(QuestionnaireView_Previews.self)
    }
}
