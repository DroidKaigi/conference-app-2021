import DroidKaigiMPP

public struct User: Equatable {
    public var idToken: String?

    public init(idToken: String?) {
        self.idToken = idToken
    }

    public init(from model: DroidKaigiMPP.User) {
        self.idToken = model.idToken
    }
}
