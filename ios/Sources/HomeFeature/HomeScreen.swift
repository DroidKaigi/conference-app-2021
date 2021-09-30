import Component
import ComposableArchitecture
import Model
import Repository
import Styleguide
import SwiftUI

public struct HomeScreen: View {
    private let store: Store<HomeState, HomeAction>

    public init(store: Store<HomeState, HomeAction>) {
        self.store = store
    }

    public var body: some View {
        NavigationView {
            InlineTitleNavigationBarScrollView {
                    WithViewStore(store) { viewStore in
                        VStack(alignment: .trailing, spacing: 0) {
                            Spacer(minLength: 16)
                            if let topic = viewStore.topic {
                                LargeCard(
                                    content: topic,
                                    language: viewStore.language,
                                    tapAction: {
                                        viewStore.send(.tap(topic))
                                    },
                                    tapFavoriteAction: {
                                        viewStore.send(.tapFavorite(isFavorited: topic.isFavorited, id: topic.id))
                                    },
                                    tapPlayAction: {
                                        viewStore.send(.tapPlay(topic))
                                    }
                                )
                            }
                            Separator()
                            QuestionnaireView(tapAnswerAction: {
                                viewStore.send(.answerQuestionnaire)
                            })
                            Separator()
                            ForEach(viewStore.listFeedContents) { feedContent in
                                ListItem(
                                    content: feedContent,
                                    language: viewStore.language,
                                    tapAction: {
                                        viewStore.send(.tap(feedContent))
                                    },
                                    tapFavoriteAction: {
                                        viewStore.send(.tapFavorite(isFavorited: feedContent.isFavorited, id: feedContent.id))
                                    },
                                    tapPlayAction: {
                                        viewStore.send(.tapPlay(feedContent))
                                    }
                                )
                            }
                        }
                        .separatorStyle(ThickSeparatorStyle())
                }
            }
            .background(AssetColor.Background.primary.color.ignoresSafeArea())
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    AssetImage.logoTitle.image
                }
            }
            .navigationBarItems(
                trailing: Button(action: {
                    ViewStore(store).send(.showSetting)
                }, label: {
                    AssetImage.iconSetting.image
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                })
            )
        }
    }
}

private extension LargeCard {
    init(
        content: FeedContent,
        language: Lang,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void,
        tapPlayAction: @escaping () -> Void
    ) {
        self.init(
            title: content.item.title.get(by: language),
            imageURL: URL(string: content.item.image.largeURLString),
            media: content.item.media,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoriteAction,
            tapPlayAction: tapPlayAction
        )
    }
}

private extension ListItem {
    init(
        content: FeedContent,
        language: Lang,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void,
        tapPlayAction: @escaping () -> Void
    ) {
        let speakers = (content.item.wrappedValue as? Podcast)?.speakers ?? []
        self.init(
            title: content.item.title.get(by: language),
            media: content.item.media,
            imageURL: URL(string: content.item.image.smallURLString),
            speakers: speakers,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapFavoriteAction: tapFavoriteAction,
            tapAction: tapAction,
            tapPlayAction: tapPlayAction
        )
    }
}

#if DEBUG
public struct HomeScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            HomeScreen(
                store: .init(
                    initialState: .init(
                        feedContents: [
                            .blogMock(),
                            .blogMock(),
                            .blogMock(),
                            .blogMock(),
                            .blogMock(),
                            .blogMock()
                        ],
                        language: .en
                    ),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)
        }
    }
}
#endif
