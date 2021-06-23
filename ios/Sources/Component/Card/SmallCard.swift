import Model
import SwiftUI
import Styleguide

public struct SmallCard: View {
    private let title: String
    private let imageURL: URL?
    private let tag: Media
    private let date: Date
    private let isFavorited: Bool
    private let tapAction: () -> Void
    private let tapFavoriteAction: () -> Void

    public init(
        title: String,
        imageURL: URL?,
        tag: Media,
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
        VStack(alignment: .leading, spacing: 16) {
            ImageView(
                imageURL: imageURL,
                placeholder: .noImage,
                placeholderSize: .small
            )
            .aspectRatio(163/114, contentMode: .fill)

            VStack(alignment: .leading, spacing: 12) {
                VStack(alignment: .leading, spacing: 4) {
                    Text(title)
                        .font(.subheadline)
                        .foregroundColor(AssetColor.Base.primary.color)
                        .lineLimit(3)
                        .frame(maxHeight: .infinity, alignment: .top)

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
                        let image = isFavorited ? AssetImage.iconFavorite.image : AssetImage.iconFavoriteOff.image
                        image
                            .renderingMode(.template)
                            .foregroundColor(AssetColor.primary.color)
                    })
                }
            }
        }
        .padding(8)
        .background(Color.clear)
        .onTapGesture(perform: tapAction)
    }
}

public struct SmallCard_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            SmallCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .droidKaigiFm,
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
                tag: .medium,
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
                tag: .youtube,
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
                tag: .droidKaigiFm,
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
                tag: .medium,
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
                tag: .youtube,
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
