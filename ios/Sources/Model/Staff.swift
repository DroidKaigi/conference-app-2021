import DroidKaigiMPP

public struct Staff: Equatable, Identifiable {
    public var id: Int
    public var iconURLString: String
    public var username: String
    public var profileURLString: String

    public init(
        id: Int,
        iconURLString: String,
        username: String,
        profileURLString: String
    ) {
        self.id = id
        self.iconURLString = iconURLString
        self.username = username
        self.profileURLString = profileURLString
    }

    public init(from model: DroidKaigiMPP.Staff) {
        self.id = Int(model.id)
        self.iconURLString = model.iconUrl
        self.username = model.username
        self.profileURLString = model.profileUrl
    }
}

#if DEBUG
public extension Staff {
    static func mock(
        id: Int = UUID().uuidString.hash,
        iconURLString: String = "https://example.com",
        username: String = "dummy username",
        profileURLString: String = "https://github.com"
    ) -> Self {
        .init(
            id: id,
            iconURLString: iconURLString,
            username: username,
            profileURLString: profileURLString
        )
    }
}
#endif
