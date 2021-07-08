import Model
import SwiftUI
import Styleguide

public struct SmallCard: View {
    enum Const {
        static let cardWidth = (UIScreen.main.bounds.width - 8) / 2
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
        GeometryReader { geometry in
            VStack(alignment: .leading, spacing: 16) {
                ImageView(
                    imageURL: imageURL,
                    placeholder: .noImage,
                    placeholderSize: .small,
                    width: geometry.size.width,
                    height: geometry.size.width * 114/163
                )

                VStack(alignment: .leading, spacing: 12) {
                    VStack(alignment: .leading, spacing: .zero) {
                        Text(title)
                            .font(.subheadline)
                            .foregroundColor(AssetColor.Base.primary.color)
                            .lineLimit(3)
                            .frame(maxHeight: .infinity, alignment: .top)

                        Spacer(minLength: 4)

                        Text(date.formatted)
                            .font(.caption)
                            .foregroundColor(AssetColor.Base.tertiary.color)
                    }

                    HStack(spacing: 8) {
                        Tag(media: media)

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
        }
        .padding(8)
        .frame(width: Const.cardWidth, height: Const.cardWidth * 278/179)
    }
}

public struct SmallCard_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .droidKaigiFm,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .light)

            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .medium,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .light)

            SmallCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                media: .youtube,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .light)

            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .droidKaigiFm,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .dark)

            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .medium,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .dark)

            SmallCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                media: .youtube,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
