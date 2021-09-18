import Model
import Styleguide
import SwiftUI

public struct ListItem: View {
    private struct Const {
        static let maximumShowingSpeaker = 7
    }

    private let title: String
    private let media: Media
    private let imageURL: URL?
    private let speakers: [Speaker]
    private let date: Date
    private let isFavorited: Bool
    private let tapFavoriteAction: () -> Void
    private let tapAction: () -> Void

    public init(
        title: String,
        media: Media,
        imageURL: URL?,
        speakers: [Speaker],
        date: Date,
        isFavorited: Bool,
        tapFavoriteAction: @escaping () -> Void,
        tapAction: @escaping () -> Void
    ) {
        self.title = title
        self.media = media
        self.imageURL = imageURL
        self.speakers = speakers
        self.date = date
        self.isFavorited = isFavorited
        self.tapFavoriteAction = tapFavoriteAction
        self.tapAction = tapAction
    }

    public var body: some View {
        VStack(alignment: .leading) {
            Tag(media: media)

            HStack(alignment: .top) {
                VStack(spacing: 8) {
                    ZStack {
                        ImageView(
                            imageURL: imageURL,
                            placeholderSize: .small,
                            width: 100,
                            height: 100
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
                }
                VStack(alignment: .leading) {
                    Text(title)
                        .font(.headline)
                        .foregroundColor(AssetColor.Base.primary.color)
                        .lineLimit(speakers.isEmpty ? 3 : 2)
                    Spacer(minLength: 8)
                    if !speakers.isEmpty {
                        HStack(spacing: -4) {
                            ForEach(Array(speakers.enumerated()), id: \.0) { (index, speaker) in
                                if index > Const.maximumShowingSpeaker {
                                    EmptyView()
                                } else {
                                    AvatarView(avatarImageURL: URL(string: speaker.iconURLString), style: .small)
                                        .zIndex(Double(Const.maximumShowingSpeaker - index))
                                }
                            }
                            if speakers.count > Const.maximumShowingSpeaker {
                                Text("+\(speakers.count - Const.maximumShowingSpeaker)")
                                    .font(.caption)
                                    .padding(4)
                                    .background(AssetColor.Background.contents.color)
                                    .clipShape(Circle())
                                    .overlay(
                                        Circle()
                                            .stroke(AssetColor.Separate.image.color, lineWidth: 1)
                                    )
                                    .zIndex(Double(-Const.maximumShowingSpeaker))
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

#if DEBUG
public struct ListItem_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            ListItem(
                title: "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイ...",
                media: .droidKaigiFm(isPlaying: false),
                imageURL: nil,
                speakers: [],
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
                media: .droidKaigiFm(isPlaying: false),
                imageURL: nil,
                speakers: [],
                date: Date(timeIntervalSince1970: 0),
                isFavorited: true,
                tapFavoriteAction: {},
                tapAction: {}
            )
            .frame(width: 343, height: 132)
            .environment(\.colorScheme, .light)
            ListItem(
                title: "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイ...",
                media: .droidKaigiFm(isPlaying: false),
                imageURL: nil,
                speakers: Array(repeating: .mock(), count: 8),
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
                media: .droidKaigiFm(isPlaying: false),
                imageURL: nil,
                speakers: Array(repeating: .mock(), count: 8),
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
#endif
