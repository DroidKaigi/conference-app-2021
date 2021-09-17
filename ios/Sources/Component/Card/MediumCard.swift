import Model
import SwiftUI
import Styleguide

public struct MediumCard: View {
    enum Const {
        static let margin: CGFloat = 16
        static let cardWidth: CGFloat = 257
        static let cardHeight: CGFloat = 258
        static let imageViewWidth = Const.cardWidth - (Const.margin * 2) - (ImageView.Const.roundedLineWidth * 2)
    }

    private let title: String
    private let imageURL: URL?
    private let media: Media
    private let date: Date
    private let isFavorited: Bool
    private let tapAction: () -> Void
    private let tapFavoriteAction: () -> Void

    public init(
        title: String,
        imageURL: URL?,
        media: Media,
        date: Date,
        isFavorited: Bool,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        self.title = title
        self.imageURL = imageURL
        self.media = media
        self.date = date
        self.isFavorited = isFavorited
        self.tapAction = tapAction
        self.tapFavoriteAction = tapFavoriteAction
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 13) {
            ZStack {
                ImageView(
                    imageURL: imageURL,
                    placeholder: .noImage,
                    placeholderSize: .medium,
                    width: Const.imageViewWidth,
                    height: Const.imageViewWidth * 114/225
                )
                .clipShape(RoundedRectangle(cornerRadius: 20))
                if case let .droidKaigiFm(isPlaying) = media {
                    SwiftUI.Image(
                        systemName: isPlaying ? "stop.fill" : "play.fill"
                    )
                    .foregroundColor(Color.white)
                    .padding()
                    .background(Color.black.opacity(0.4))
                    .clipShape(Circle())
                }
            }

            VStack(alignment: .leading, spacing: 12) {
                Group {
                    Text(title)
                        .font(.subheadline)
                        .foregroundColor(AssetColor.Base.primary.color)
                        .lineLimit(2)

                    Spacer(minLength: 4)

                    Text(date.formatted)
                        .font(.caption)
                        .foregroundColor(AssetColor.Base.tertiary.color)
                }

                HStack(spacing: 8) {
                    Tag(media: media)

                    Spacer()

                    Button(action: tapFavoriteAction, label: {
                        let image = isFavorited
                            ? AssetImage.iconFavorite.image
                            : AssetImage.iconFavoriteOff.image
                        image
                            .renderingMode(.template)
                            .foregroundColor(AssetColor.primary.color)
                    })
                }
            }
        }
        .padding(Const.margin)
        .background(Color.clear)
        .onTapGesture(perform: tapAction)
        .frame(width: Const.cardWidth, height: Const.cardHeight)
    }
}

public struct MediumCard_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
                MediumCard(
                    title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                    imageURL: URL(string: ""),
                    media: .droidKaigiFm(isPlaying: false),
                    date: Date(timeIntervalSince1970: 0),
                    isFavorited: false,
                    tapAction: {},
                    tapFavoriteAction: {}
                )
                .background(AssetColor.Background.primary.color)
                .previewDevice(.init(rawValue: "iPhone X"))
                .environment(\.colorScheme, colorScheme)

                MediumCard(
                    title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                    imageURL: URL(string: ""),
                    media: .medium,
                    date: Date(timeIntervalSince1970: 0),
                    isFavorited: true,
                    tapAction: {},
                    tapFavoriteAction: {}
                )
                .background(AssetColor.Background.primary.color)
                .previewDevice(.init(rawValue: "iPhone X"))
                .environment(\.colorScheme, colorScheme)

                MediumCard(
                    title: "タイトル",
                    imageURL: URL(string: ""),
                    media: .youtube,
                    date: Date(timeIntervalSince1970: 0),
                    isFavorited: true,
                    tapAction: {},
                    tapFavoriteAction: {}
                )
                .background(AssetColor.Background.primary.color)
                .previewDevice(.init(rawValue: "iPhone X"))
                .environment(\.colorScheme, colorScheme)
            }
        }
        .previewLayout(.sizeThatFits)
    }
}
