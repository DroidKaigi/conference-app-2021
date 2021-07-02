import Model
import SwiftUI
import Styleguide

public struct LargeCard: View {
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
        GeometryReader { geometry in
            VStack(alignment: .leading, spacing: 16) {
                ImageView(
                    imageURL: imageURL,
                    placeholder: .noImage,
                    placeholderSize: .large,
                    width: geometry.size.width,
                    height: geometry.size.width * 190/343
                )

                VStack(alignment: .leading, spacing: 13) {
                    Text(title)
                        .font(.headline)
                        .foregroundColor(AssetColor.Base.primary.color)
                        .lineLimit(2)

                    HStack(spacing: 8) {
                        Tag(type: tag) {
                            // do something if needed
                        }

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
        }
        .padding(16)
        .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.width * 319/375)
    }
}

public struct LargeCard_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            LargeCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .droidKaigiFm,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 375, height: 319)
            .environment(\.colorScheme, .light)

            LargeCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .medium,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 375, height: 319)
            .environment(\.colorScheme, .light)

            LargeCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                tag: .youtube,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 375, height: 319)
            .environment(\.colorScheme, .light)

            LargeCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .droidKaigiFm,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 375, height: 319)
            .environment(\.colorScheme, .dark)

            LargeCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .medium,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 375, height: 319)
            .environment(\.colorScheme, .dark)

            LargeCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                tag: .youtube,
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 375, height: 319)
            .environment(\.colorScheme, .dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
