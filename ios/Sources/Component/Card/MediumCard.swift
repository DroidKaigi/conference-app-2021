import Model
import SwiftUI
import Styleguide

public struct MediumCard: View {
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
            VStack(alignment: .leading, spacing: 13) {
                ImageView(
                    imageURL: imageURL,
                    placeholder: .noImage,
                    placeholderSize: .medium,
                    width: geometry.size.width,
                    height: geometry.size.width * 114/225
                )

                VStack(alignment: .leading, spacing: 12) {
                    VStack(alignment: .leading, spacing: 4) {
                        Text(title)
                            .font(.subheadline)
                            .foregroundColor(AssetColor.Base.primary.color)
                            .lineLimit(2)
                            .frame(maxHeight: .infinity, alignment: .top)

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
        .padding(16)
        .frame(width: 257, height: 258)
    }
}

public struct MediumCard_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            MediumCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .droidKaigiFm,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)
            .environment(\.colorScheme, .light)

            MediumCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .medium,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)
            .environment(\.colorScheme, .light)

            MediumCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                media: .youtube,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)
            .environment(\.colorScheme, .light)

            MediumCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .droidKaigiFm,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)
            .environment(\.colorScheme, .dark)

            MediumCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                media: .medium,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)
            .environment(\.colorScheme, .dark)

            MediumCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                media: .youtube,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)
            .environment(\.colorScheme, .dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
