import SnapshotTesting

public enum TestDevice: String {
    case iPhoneX
    case iPhone8
    case iPhoneSe

    var config: ViewImageConfig {
        switch self {
        case .iPhoneX:
            return .iPhoneX
        case .iPhone8:
            return .iPhone8
        case .iPhoneSe:
            return .iPhoneSe
        }
    }
}
