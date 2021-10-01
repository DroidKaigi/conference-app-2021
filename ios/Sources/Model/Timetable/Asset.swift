import DroidKaigiMPP

public struct TimetableAsset: Equatable {
    public var videoURLString: String?
    public var slideURLString: String?

    public init(
        videoURLString: String?,
        slideURLString: String?
    ) {
        self.videoURLString = videoURLString
        self.slideURLString = slideURLString
    }

    public init(from model: DroidKaigiMPP.TimetableAsset) {
        self.init(
            videoURLString: model.videoUrl,
            slideURLString: model.slideUrl
        )
    }
}
