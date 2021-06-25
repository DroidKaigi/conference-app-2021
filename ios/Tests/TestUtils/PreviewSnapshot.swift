import SnapshotTesting
import SwiftUI
import XCTest

/// Asserts that a given preview contents match on disk.
///
/// - Parameters:
///   - target: Target preview type
///   - recording: Whether or not to record a new reference. If component that was already recorded, make this property to `true` and re-test.
///   - file: The file in which failure occurred. Defaults to the file name of the test case in which this function was called.
///   - testName: The name of the test in which failure occurred. Defaults to the function name of the test case in which this function was called.
///   - line: The line number on which failure occurred. Defaults to the line number on which this function was called.
public func assertPreviewSnapshot<T: PreviewProvider>(
    _ target: T.Type,
    record recording: Bool = false,
    file: StaticString = #file,
    testName: String = #function,
    line: UInt = #line
) {
    for preview in T._allPreviews {
        assertSnapshot(
            matching: preview.content,
            as: .image,
            record: recording,
            file: file,
            testName: testName,
            line: line
        )
    }
}

public func assertPreviewScreenSnapshot<T: PreviewProvider>(
    _ target: T.Type,
    with devices: [TestDevice] = [.iPhoneX, .iPhone8, .iPhoneSe],
    record recording: Bool = false,
    file: StaticString = #file,
    testName: String = #function,
    line: UInt = #line
) {
    var deviceCounter: [TestDevice:Int] = [:]
    for preview in T._allPreviews {
        let vc = UIHostingController(rootView: preview.content)
        for device in devices {
            deviceCounter[device, default: 0] += 1
            assertSnapshot(
                matching: vc,
                as: .image(on: device.config),
                named: "\(device).\(deviceCounter[device, default: 0])",
                record: recording,
                file: file,
                testName: testName,
                line: line
            )
        }
    }
}
