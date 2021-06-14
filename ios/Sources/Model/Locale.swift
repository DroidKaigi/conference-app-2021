import DroidKaigiMPP

public enum Locale: Equatable {
    case japan
    case other

    public static func from(_ model: DroidKaigiMPP.Locale) -> Locale {
        switch model {
        case .japan:
            return .japan
        case .other:
            return .other
        default:
            assertionFailure()
            return .other
        }
    }
}
