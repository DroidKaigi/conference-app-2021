import DroidKaigiMPP
import Foundation

public struct MultiLangText: Equatable {
    public var enTitle: String
    public var jaTitle: String

    public init(
        enTitle: String,
        jaTitle: String
    ) {
        self.enTitle = enTitle
        self.jaTitle = jaTitle
    }

    public init(from model: DroidKaigiMPP.MultiLangText) {
        self.enTitle = model.enTitle
        self.jaTitle = model.jaTitle
    }

    public func get(by lang: Lang) -> String {
        switch lang {
        case .en:
            return enTitle
        case .ja:
            return jaTitle
        case .system:
            let locale = Foundation.Locale(identifier: Foundation.Locale.preferredLanguages[0])
            return locale.languageCode == "ja" ? jaTitle : enTitle
        }
    }
}
