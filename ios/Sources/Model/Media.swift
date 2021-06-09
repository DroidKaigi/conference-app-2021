import DroidKaigiMPP

public enum Media: Equatable {
    case droidkaigifm(String)
    case medium(String)
    case youtube(String)
    case other(String)

    static func from(_ model: DroidKaigiMPP.Media) -> Media {
        switch model {
        case let fm as DroidKaigiMPP.Media.DroidKaigiFM:
            return .droidkaigifm(fm.text)
        case let medium as DroidKaigiMPP.Media.Medium:
            return .medium(medium.text)
        case let youtube as DroidKaigiMPP.Media.YouTube:
            return .youtube(youtube.text)
        case let other as DroidKaigiMPP.Media.Other:
            return .other(other.text)
        default:
            assertionFailure()
            return .other("")
        }
    }
}
