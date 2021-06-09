import DroidKaigiMPP

public struct MultiLangText: Equatable {
    public var enTitle: String
    public var jaTitle: String
    public var currentLangTitle: String

    public init(
        enTitle: String,
        jaTitle: String,
        currentLangTitle: String
    ) {
        self.enTitle = enTitle
        self.jaTitle = jaTitle
        self.currentLangTitle = currentLangTitle
    }

    public init(from model: DroidKaigiMPP.MultiLangText) {
        self.enTitle = model.enTitle
        self.jaTitle = model.jaTitle
        self.currentLangTitle = model.currentLangTitle
    }

    public func getByLang(lang: Lang) -> String {
        switch lang {
        case .en:
            return enTitle
        case .ja:
            return jaTitle
        }
    }
}
