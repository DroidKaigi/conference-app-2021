import DroidKaigiMPP

public enum Theme: Equatable {
    case system
    case dark
    case light

    public static func from(_ model: DroidKaigiMPP.Theme) -> Theme {
        switch model {
        case .system:
            return .system
        case .dark:
            return .dark
        case .light:
            return .light
        default:
            assertionFailure()
            return .system
        }
    }

    public var kmmTheme: DroidKaigiMPP.Theme {
        switch self {
        case .system:
            return .system
        case .dark:
            return .dark
        case .light:
            return .light
        }
    }
}
