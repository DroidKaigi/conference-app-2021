import DroidKaigiMPP

public enum Media: Hashable, Equatable, CaseIterable {
    case droidKaigiFm(isPlaying: Bool)
    case medium
    case youtube
    case other

    public static var allCases: [Self] = [
        .droidKaigiFm(isPlaying: false),
        .medium,
        .youtube,
        .other,
    ]

    static func from(_ model: DroidKaigiMPP.Media) -> Media {
        switch model {
        case is DroidKaigiMPP.Media.DroidKaigiFM:
            return .droidKaigiFm(isPlaying: false)
        case is DroidKaigiMPP.Media.Medium:
            return .medium
        case is DroidKaigiMPP.Media.YouTube:
            return .youtube
        case is DroidKaigiMPP.Media.Other:
            return .other
        default:
            assertionFailure()
            return .other
        }
    }

    public var isPodcast: Bool {
        switch self {
        case .droidKaigiFm:
            return true
        case .medium, .youtube, .other:
            return false
        }
    }
}
