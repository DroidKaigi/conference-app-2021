import HomeFeature
import TestUtils
import XCTest

final class HomeFeatureTests: XCTestCase {
    func testHomeScreen() {
        assertPreviewSnapshot(HomeScreen_Previews.self, with: .iPhoneX)
    }

    func testQuestionnaireView() {
        assertPreviewSnapshot(QuestionnaireView_Previews.self)
    }
}
