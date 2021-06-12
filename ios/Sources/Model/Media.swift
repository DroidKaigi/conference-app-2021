import DroidKaigiMPP

public enum Media: Equatable {
    case droidkaigifm
    case medium
    case youtube
    case other

    static func from(_ model: DroidKaigiMPP.Media) -> Media {
        switch model {
        case is DroidKaigiMPP.Media.DroidKaigiFM:
            return .droidkaigifm
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
}
