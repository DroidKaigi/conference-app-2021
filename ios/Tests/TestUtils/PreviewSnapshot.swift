import SnapshotTesting
import SwiftUI
import XCTest

public func assertPreviewSnapshot<T: PreviewProvider>(
    _ tareget: T.Type,
    file: StaticString = #file,
    testName: String = #function,
    line: UInt = #line
) {
    for preview in T._allPreviews {
        assertSnapshot(
            matching: preview.content,
            as: .image,
            file: file,
            testName: testName,
            line: line
        )
    }
}
