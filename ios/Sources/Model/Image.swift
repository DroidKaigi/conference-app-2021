import DroidKaigiMPP

public struct Image: Equatable {
    public var largeURLString: String
    public var smallURLString: String
    public var standardURLString: String

    public init(
        largeURLString: String,
        smallURLString: String,
        standardURLString: String
    ) {
        self.largeURLString = largeURLString
        self.smallURLString = smallURLString
        self.standardURLString = standardURLString
    }

    public init(from model: DroidKaigiMPP.Image) {
        self.largeURLString = model.largeUrl
        self.smallURLString = model.smallUrl
        self.standardURLString = model.standardUrl
    }
}
