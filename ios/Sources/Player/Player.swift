import AVFoundation
import ModernAVPlayer

public protocol PlayerProtocol {
    var isPlaying: Bool { get }
    func setUpPlayer(url: URL)
    func play()
    func stop()
}

public struct Player: PlayerProtocol {
    private let player: ModernAVPlayer = ModernAVPlayer(
        config: Configuration(),
        loggerDomains: [.error, .unavailableCommand]
    )

    public var isPlaying: Bool {
        player.state == .some(.playing)
    }

    public init() {}

    public func setUpPlayer(url: URL) {
        self.player.load(media: ModernAVPlayerMedia(url: url, type: .clip), autostart: true)
    }

    public func play() {
        self.player.play()
    }

    public func stop() {
        self.player.stop()
    }
}

internal struct Configuration: PlayerConfiguration {

    // Buffering State
    let rateObservingTimeout: TimeInterval = 3
    let rateObservingTickTime: TimeInterval = 0.3

    // General Audio preferences
    let preferredTimescale = CMTimeScale(NSEC_PER_SEC)
    let periodicPlayingTime: CMTime
    let audioSessionCategory = AVAudioSession.Category.playback
    let audioSessionCategoryOptions: AVAudioSession.CategoryOptions = .init()

    // Reachability Service
    let reachabilityURLSessionTimeout: TimeInterval = 3
    let reachabilityNetworkTestingURL = URL(string: "https://www.google.com")!
    let reachabilityNetworkTestingTickTime: TimeInterval = 3
    let reachabilityNetworkTestingIteration: UInt = 10

    // RemoteCommandExample is used for example
    var useDefaultRemoteCommand = false

    let allowsExternalPlayback = false

    // AVPlayerItem Init Service
    let itemLoadedAssetKeys = ["playable", "duration"]

    init() {
        periodicPlayingTime = CMTime(seconds: 0.1, preferredTimescale: preferredTimescale)
    }
}
