import DroidKaigiMPP

public struct Staff: Equatable, Identifiable {
    public var id: Int
    public var imageURLString: String
    public var name: String
    public var urlString: String

    public init(
        id: Int,
        imageURLString: String,
        name: String,
        urlString: String
    ) {
        self.id = id
        self.imageURLString = imageURLString
        self.name = name
        self.urlString = urlString
    }

    public init(from model: DroidKaigiMPP.Staff) {
        self.id = Int(model.id)
        self.imageURLString = model.iconUrl
        self.name = model.username
        self.urlString = model.profileUrl
    }
}

#if DEBUG
public extension Staff {
    static func mock(
        id: Int = UUID().uuidString.hash,
        imageURLString: String = "https://example.com",
        name: String = "dummy name",
        urlString: String = "https://github.com"
    ) -> Self {
        .init(
            id: id,
            imageURLString: imageURLString,
            name: name,
            urlString: urlString
        )
    }
}
#endif
