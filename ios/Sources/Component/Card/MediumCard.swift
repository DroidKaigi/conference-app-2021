import SwiftUI
import Styleguide
import Utility

public struct MediumCard: View {
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
                VStack(alignment: .leading, spacing: 13) {
                    ImageView(imageURL: imageURL, width: 225, height: 114)

                    VStack(alignment: .leading, spacing: 12) {
                        VStack(alignment: .leading, spacing: 4) {
                            Text(title)
                                .font(.subheadline)
                                .foregroundColor(Color(AssetColor.Base.primary.color))
                                .lineLimit(2)

                            Text(date.formatted)
                                .font(.caption)
                                .foregroundColor(Color(AssetColor.Base.tertiary.color))
                        }

                        HStack(spacing: 8) {
                            Tag(type: tag) {
                                // do something if needed
                            }

                            Spacer()

                            Button(action: tapFavoriteAction, label: {
                                if isFavorited {
                                    Image(uiImage: AssetImage.iconFavorite.image.withRenderingMode(.alwaysTemplate).withTintColor(AssetColor.primary.color))
                                } else {
                                    Image(uiImage: AssetImage.iconFavoriteOff.image.withRenderingMode(.alwaysTemplate).withTintColor(AssetColor.primary.color))
                                }
                            })
                        }
                    }
                }
            }
            .padding(16)
        })
    }
}

public struct MediumCard_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            MediumCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .droidKaigiFm,
                date: Date(),
                isFavorited: false,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)

            MediumCard(
                title: "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト...",
                imageURL: URL(string: ""),
                tag: .medium,
                date: Date(),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)

            MediumCard(
                title: "タイトル",
                imageURL: URL(string: ""),
                tag: .youtube,
                date: Date(),
                isFavorited: true,
                tapAction: {},
                tapFavoriteAction: {}
            )
            .frame(width: 257, height: 258)
        }
        .previewLayout(.sizeThatFits)
    }
}
