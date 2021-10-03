import DroidKaigiMPP

public enum Lang: Equatable, CaseIterable {
    case system
    case ja
    case en

    public static func from(_ model: DroidKaigiMPP.Lang?) -> Lang {
        guard let lang = model else { return .system }
        switch lang {
        case .ja:
            return .ja
        case .en:
            return .en
        case .system:
            return .system
        default:
            return .system
        }
    }

    public var kmmLang: DroidKaigiMPP.Lang {
        switch self {
        case .ja:
            return .ja
        case .en:
            return .en
        case .system:
            return .system
        }
    }

    public var value: String? {
        switch self {
        case .system:
            return nil
        case .ja:
            return "ja"
        case .en:
            return "en"
        }
    }
}
