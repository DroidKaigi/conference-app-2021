import DroidKaigiMPP

public struct Speaker: Equatable {
    public var name: String
    public var iconURLString: String

    public init(
        name: String,
        iconURLString: String
    ) {
        self.name = name
        self.iconURLString = iconURLString
    }

    public init(from model: DroidKaigiMPP.Speaker) {
        self.name = model.name
        self.iconURLString = model.iconUrl
    }
}

public extension Speaker {
    var kmmModel: DroidKaigiMPP.Speaker {
        .init(name: name, iconUrl: iconURLString)
    }
}
