import DroidKaigiMPP

public struct DeviceInfo: Equatable, Identifiable {
    public var id: String
    public var isPushSupported: Bool

    public init(id: String, isPushSupported: Bool) {
        self.id = id
        self.isPushSupported = isPushSupported
    }

    public init(from model: DroidKaigiMPP.DeviceInfo) {
        self.id = model.id
        self.isPushSupported = model.isPushSupported
    }
}
