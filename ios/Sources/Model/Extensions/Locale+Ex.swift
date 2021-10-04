import Foundation

public extension Foundation.Locale {
    var language: Lang {
        switch languageCode {
        case "ja":
            return .ja
        case "en":
            return .en
        default:
            return .system
        }
    }
}
