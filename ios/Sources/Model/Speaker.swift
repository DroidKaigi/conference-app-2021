import DroidKaigiMPP

public struct Speaker: Equatable, Hashable {
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

    public init(from model: DroidKaigiMPP.TimetableSpeaker) {
        self.name = model.name
        self.iconURLString = model.iconUrl ?? ""
    }
}

#if DEBUG
public extension Speaker {
    static func mock(
        name: String = "Mr. Droid",
        iconURLString: String = ""
    ) -> Self {
        .init(
            name: name,
            iconURLString: iconURLString
        )
    }
}
#endif
