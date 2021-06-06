import SwiftUI
import Styleguide

public struct SmallCard: View {
    private let title: String
    private let imageURL: URL?
    private let tag: TagType
    private let date: Date
    private let isFavorited: Bool
    private let tapAction: () -> Void
    private let tapFavoriteAction: () -> Void

    public init(
        title: String,
        imageURL: URL?,
        tag: TagType,
        date: Date,
        isFavorited: Bool,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        self.title = title
        self.imageURL = imageURL
        self.tag = tag
        self.date = date
        self.isFavorited = isFavorited
        self.tapAction = tapAction
        self.tapFavoriteAction = tapFavoriteAction
    }

    public var body: some View {
        Button(action: tapAction, label: {
            VStack(alignment: .leading, spacing: 16) {
                ImageView(imageURL: imageURL)
                    .aspectRatio(163/114, contentMode: .fit)

                VStack(alignment: .leading, spacing: 12) {
                    VStack(alignment: .leading, spacing: 4) {
                        Text(title)
                            .font(.subheadline)
                            .foregroundColor(AssetColor.Base.primary.color)
                            .lineLimit(3)

                        Text(date.formatted)
                            .font(.caption)
                            .foregroundColor(AssetColor.Base.tertiary.color)
                    }

                    HStack(spacing: 8) {
                        Tag(type: tag) {
                            // do something if needed
                        }

                        Spacer()

                        Button(action: tapFavoriteAction, label: {
                            let uiImage = isFavorited ? AssetImage.iconFavorite.image : AssetImage.iconFavoriteOff.image
                            Image(uiImage: uiImage)
                                .renderingMode(.template)
                                .foregroundColor(AssetColor.primary.color)
                        })
                    }
                }
            }
            .padding(8)
            .background(AssetColor.Background.primary.color)
        })
    }
}

struct SmallCard_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .droidKaigiFm,
                date: Date(),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .light)

            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .medium,
                date: Date(),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .light)

            SmallCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                tag: .youtube,
                date: Date(),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .light)

            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .droidKaigiFm,
                date: Date(),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .dark)

            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .medium,
                date: Date(),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 179, height: 278)
            .environment(\.colorScheme, .dark)

            SmallCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                tag: .youtube,
                date: Date(),
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
