import DroidKaigiMPP

public struct Author: Equatable {
    public var link: String
    public var name: String

    public init(link: String, name: String) {
        self.link = link
        self.name = name
    }

    public init(from model: DroidKaigiMPP.Author) {
        self.link = model.link
        self.name = model.name
    }
}
