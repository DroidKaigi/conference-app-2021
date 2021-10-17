import Model
import SwiftUI
import Styleguide

public struct LargeCard: View {
    enum Const {
        static let margin: CGFloat = 16
        static let cardWidth = UIScreen.main.bounds.width
        static let imageViewWidth = Const.cardWidth - (Const.margin * 2) - (ImageView.Const.roundedLineWidth * 2)
    }

    private let title: String
    private let imageURL: URL?
    private let media: Media
    private let date: Date
    private let isFavorited: Bool
    private let tapAction: () -> Void
    private let tapFavoriteAction: () -> Void
    private let tapPlayAction: () -> Void

    public init(
        title: String,
        imageURL: URL?,
        media: Media,
        date: Date,
        isFavorited: Bool,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void,
        tapPlayAction: @escaping () -> Void
    ) {
        self.title = title
        self.imageURL = imageURL
        self.media = media
        self.date = date
        self.isFavorited = isFavorited
        self.tapAction = tapAction
        self.tapFavoriteAction = tapFavoriteAction
        self.tapPlayAction = tapPlayAction
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            ZStack {
                ImageView(
                    imageURL: imageURL,
                    placeholder: .noImage,
                    placeholderSize: .large,
                    width: Const.imageViewWidth,
                    height: Const.imageViewWidth * 190/343,
                    allowsHitTesting: !media.isPodcast
                )
                if case let .droidKaigiFm(isPlaying) = media {
                    SwiftUI.Image(
                        systemName: isPlaying ? "stop.fill" : "play.fill"
                    )
                    .foregroundColor(Color.white)
                    .padding()
                    .background(Color.black.opacity(0.4))
                    .clipShape(Circle())
                    .onTapGesture(perform: tapPlayAction)
                }
            }

            VStack(alignment: .leading, spacing: 13) {
                Text(title)
                    .font(.headline)
                    .foregroundColor(AssetColor.Base.primary.color)
                    .lineLimit(2)

                HStack(spacing: 8) {
                    Tag(media: media)
                        .frame(height: 24)

                    Text(date.formatted)
                        .font(.caption)
                        .foregroundColor(AssetColor.Base.tertiary.color)

                    Spacer()

                    Button(action: tapFavoriteAction, label: {
                        let image = isFavorited ? AssetImage.iconFavorite.image : AssetImage.iconFavoriteOff.image
                        image
                            .renderingMode(.template)
                            .foregroundColor(AssetColor.primary.color)
                    })
                }
            }
        }
        .background(Color.clear)
        .onTapGesture(perform: tapAction)
        .padding(Const.margin)
    }
}

public struct LargeCard_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
                LargeCard(
                    title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                    imageURL: URL(string: ""),
                    media: .droidKaigiFm(isPlaying: false),
                    date: Date(timeIntervalSince1970: 0),
                    isFavorited: false,
                    tapAction: {},
                    tapFavoriteAction: {},
                    tapPlayAction: {}
                )
                .frame(width: 375, height: 319)
                .background(AssetColor.Background.primary.color)
                .previewDevice(.init(rawValue: "iPhone X"))
                .environment(\.colorScheme, colorScheme)

                LargeCard(
                    title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                    imageURL: URL(string: ""),
                    media: .medium,
                    date: Date(timeIntervalSince1970: 0),
                    isFavorited: true,
                    tapAction: {},
                    tapFavoriteAction: {},
                    tapPlayAction: {}
                )
                .frame(width: 375, height: 319)
                .background(AssetColor.Background.primary.color)
                .previewDevice(.init(rawValue: "iPhone X"))
                .environment(\.colorScheme, colorScheme)

                LargeCard(
                    title: "タイトル",
                    imageURL: URL(string: ""),
                    media: .youtube,
                    date: Date(timeIntervalSince1970: 0),
                    isFavorited: true,
                    tapAction: {},
                    tapFavoriteAction: {},
                    tapPlayAction: {}
                )
                .frame(width: 375, height: 319)
                .background(AssetColor.Background.primary.color)
                .previewDevice(.init(rawValue: "iPhone X"))
                .environment(\.colorScheme, colorScheme)
            }
        }
        .previewLayout(.sizeThatFits)
    }
}
