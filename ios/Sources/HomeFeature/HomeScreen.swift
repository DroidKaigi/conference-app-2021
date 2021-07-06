import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct HomeScreen: View {
    private let store: Store<HomeState, HomeAction>

    public init(store: Store<HomeState, HomeAction>) {
        self.store = store
    }

    public var body: some View {
        NavigationView {
            WithViewStore(store) { viewStore in
                InlineTitleNavigationBarScrollView {
                    ZStack(alignment: .top) {
                        AssetColor.primary.color
                            .frame(width: nil, height: 200)
                            .clipShape(CutCornerRectangle(targetCorners: [.topLeft], radius: 42))
                        VStack(alignment: .trailing, spacing: 0) {
                            MessageBar(title: viewStore.message)
                                .padding(.top, 16)
                                .padding(.trailing, 16)
                            if let topic = viewStore.topic {
                                LargeCard(
                                    item: topic,
                                    tapAction: {},
                                    tapFavoriteAction: {}
                                )
                            }
                            Separator()
                            QuestionnaireView(tapAnswerAction: {
                                viewStore.send(.answerQuestionnaire)
                            })
                            Separator()
                            ForEach(viewStore.listFeedItems) { feedItem in
                                ListItem(
                                    item: feedItem,
                                    tapAction: {},
                                    tapFavoriteAction: {}
                                )
                            }
                        }
                        .separatorStyle(ThickSeparatorStyle())
                    }
                }
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .principal) {
                        AssetImage.logoTitle.image
                    }
                }
                .navigationBarItems(
                    trailing: Button(action: {
                        viewStore.send(.showSettings)
                    }) {
                        AssetImage.iconSetting.image
                            .renderingMode(.template)
                            .foregroundColor(AssetColor.Base.primary.color)
                    }
                )
                .introspectViewController { viewController in
                    viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
                }
            }
        }
    }
}

public struct HomeScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            HomeScreen(
                store: .init(
                    initialState: .init(
                        feedItems: [.mock(), .mock()],
                        message: "DroidKaigi 2021 (7/31) D-7"
                    ),
                    reducer: homeReducer,
                    environment: .init()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            HomeScreen(
                store: .init(
                    initialState: .init(
                        feedItems: [.mock(), .mock()],
                        message: "DroidKaigi 2021 (7/31) D-7"
                    ),
                    reducer: homeReducer,
                    environment: .init()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}

extension HomeFeature.FeedItem {
    static func mock(
        id: String = UUID().uuidString,
        imageURLString: String = "",
        link: String = "",
        media: Media = .medium,
        publishedAt: Date = Date(timeIntervalSince1970: 0),
        summary: String = "",
        title: String = "DroidKaigi 2021とその他活動予定についてのお知らせ"
    ) -> HomeFeature.FeedItem {
        .init(id: id, imageURLString: imageURLString, link: link, media: media, publishedAt: publishedAt, summary: summary, title: title)
    }
}

private extension LargeCard {
    init(
        item: FeedItem,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        self.init(
            title: item.title,
            imageURL: URL(string: item.imageURLString),
            tag: item.media,
            date: item.publishedAt,
            isFavorited: false,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoriteAction
        )
    }
}

private extension ListItem {
    init(
        item: FeedItem,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        self.init(
            title: item.title,
            tag: item.media,
            imageURL: URL(string: item.imageURLString),
            users: [],
            date: item.publishedAt,
            isFavorited: false,
            tapFavoriteAction: tapFavoriteAction,
            tapAction: tapAction
        )
    }
}
