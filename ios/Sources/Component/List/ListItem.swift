import Model
import Styleguide
import SwiftUI

public struct ListItem: View {
    private struct Const {
        static let maximumShowingUser = 7
    }

    private let title: String
    private let tag: Media
    private let imageURL: URL?
    // TODO: Replace with real value
    private let users: [URL]
    private let date: Date
    private let isFavorited: Bool
    private let tapFavoriteAction: () -> Void
    private let tapAction: () -> Void

    public init(
        title: String,
        tag: Media,
        imageURL: URL?,
        users: [URL],
        date: Date,
        isFavorited: Bool,
        tapFavoriteAction: @escaping () -> Void,
        tapAction: @escaping () -> Void
    ) {
        self.title = title
        self.tag = tag
        self.imageURL = imageURL
        self.users = users
        self.date = date
        self.isFavorited = isFavorited
        self.tapFavoriteAction = tapFavoriteAction
        self.tapAction = tapAction
    }

    public var body: some View {
        VStack(alignment: .leading) {
            Tag(type: tag) {
                // Set action if needed.
            }
            HStack(alignment: .top) {
                VStack(spacing: 8) {
                    ImageView(
                        imageURL: imageURL,
                        placeholderSize: .small
                    )
                    .frame(width: 100, height: 100)
                }
                VStack(alignment: .leading) {
                    Text(title)
                        .font(.headline)
                        .foregroundColor(AssetColor.Base.primary.color)
                        .lineLimit(users.isEmpty ? 3 : 2)
                    Spacer(minLength: 8)
                    if !users.isEmpty {
                        HStack(spacing: -4) {
                            ForEach(Array(users.enumerated()), id: \.0) { (index, _) in
                                if index > Const.maximumShowingUser {
                                    EmptyView()
                                } else {
                                    AvatarView(avatarImageURL: nil, style: .small)
                                        .zIndex(Double(Const.maximumShowingUser - index))
                                }
                            }
                            if users.count > Const.maximumShowingUser {
                                Text("+\(users.count - Const.maximumShowingUser)")
                                    .font(.caption)
                                    .padding(4)
                                    .background(AssetColor.Background.contents.color)
                                    .clipShape(Circle())
                                    .overlay(
                                        Circle()
                                            .stroke(AssetColor.Separate.image.color, lineWidth: 1)
                                    )
                                    .zIndex(Double(-Const.maximumShowingUser))
                            }
                        }
                        .frame(width: nil, height: 24)
                    }
                    HStack {
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
        }
        .padding(16)
        .onTapGesture(perform: tapAction)
    }
}

struct ListItem_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ListItem(
                title: "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイ...",
                tag: .droidKaigiFm,
                imageURL: nil,
                users: [],
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapFavoriteAction: {},
                tapAction: {}
            )
            .frame(width: 343, height: 132)
            .background(AssetColor.Background.primary.color)
            .environment(\.colorScheme, .dark)
            ListItem(
                title: "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイ...",
                tag: .droidKaigiFm,
                imageURL: nil,
                users: [],
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapFavoriteAction: {},
                tapAction: {}
            )
            .frame(width: 343, height: 132)
            .environment(\.colorScheme, .light)
            ListItem(
                title: "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイ...",
                tag: .droidKaigiFm,
                imageURL: nil,
                users: Array(repeating: URL(string: "https://example.com")!, count: 8),
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapFavoriteAction: {},
                tapAction: {}
            )
            .frame(width: 343, height: 132)
            .background(AssetColor.Background.primary.color)
            .environment(\.colorScheme, .dark)
            ListItem(
                title: "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイ...",
                tag: .droidKaigiFm,
                imageURL: nil,
                users: Array(repeating: URL(string: "https://example.com")!, count: 8),
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapFavoriteAction: {},
                tapAction: {}
            )
            .frame(width: 343, height: 132)
            .environment(\.colorScheme, .light)
        }
        .previewLayout(.sizeThatFits)
    }
}
