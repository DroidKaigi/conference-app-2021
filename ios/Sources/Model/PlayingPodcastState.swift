import DroidKaigiMPP

public struct PlayingPodcastState: Equatable, Identifiable {
    public var id: String
    public var isPlaying: Bool
    public var urlString: String

    public init(
        id: String,
        isPlaying: Bool,
        urlString: String
    ) {
        self.id = id
        self.isPlaying = isPlaying
        self.urlString = urlString
    }

    public init(from model: DroidKaigiMPP.PlayingPodcastState) {
        self.id = model.id
        self.isPlaying = model.isPlaying
        self.urlString = model.url
    }
}
