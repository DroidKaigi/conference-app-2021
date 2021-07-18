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
                ZStack(alignment: .top) {
                    AssetColor.primary.color
                        .frame(width: nil, height: 200)
                        .clipShape(CutCornerRectangle(targetCorners: [.topLeft], radius: 42))
                    WithViewStore(store) { viewStore in
                        VStack(alignment: .trailing, spacing: 0) {
                            Spacer(minLength: 16)
                            MessageBar(title: viewStore.message)
                                .padding(.trailing, 16)
                            if let topic = viewStore.topic {
                                LargeCard(
                                    content: topic,
                                    tapAction: {
                                        viewStore.send(.selectFeedContent)
                                    },
                                    tapFavoriteAction: {
                                        viewStore.send(.tapFavorite(isFavorited: topic.isFavorited, id: topic.id))
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
                                    tapAction: {
                                        viewStore.send(.selectFeedContent)
                                    },
                                    tapFavoriteAction: {
                                        viewStore.send(.tapFavorite(isFavorited: feedContent.isFavorited, id: feedContent.id))
                                    }
                                )
                            }
                        }
                        .separatorStyle(ThickSeparatorStyle())
                    }
                }
            }
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    AssetImage.logoTitle.image
                }
            }
            .navigationBarItems(
                trailing: AssetImage.iconSetting.image
                    .renderingMode(.template)
                    .foregroundColor(AssetColor.Base.primary.color)
            )
            .introspectViewController { viewController in
                viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
            }
        }
    }
}

private extension LargeCard {
    init(
        content: FeedContent,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        self.init(
            title: content.item.title.jaTitle,
            imageURL: URL(string: content.item.image.largeURLString),
            media: content.item.media,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoriteAction
        )
    }
}

private extension ListItem {
    init(
        content: FeedContent,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        let speakers = (content.item.wrappedValue as? Podcast)?.speakers ?? []
        self.init(
            title: content.item.title.jaTitle,
            media: content.item.media,
            imageURL: URL(string: content.item.image.smallURLString),
            speakers: speakers,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapFavoriteAction: tapFavoriteAction,
            tapAction: tapAction
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
                        ]
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
