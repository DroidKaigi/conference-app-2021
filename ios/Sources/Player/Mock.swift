import Foundation

public struct PlayerMock: PlayerProtocol {
    public var isPlaying: Bool { false }

    public init() {}

    public func setUpPlayer(url: URL) {}
    public func play() {}
    public func stop() {}
}
