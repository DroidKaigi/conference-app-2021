import Component
import ComposableArchitecture
import Model
import SwiftUI

public struct HomeListView: View {
    private let store: Store<HomeListState, HomeListAction>

    public init(store: Store<HomeListState, HomeListAction>) {
        self.store = store
    }

    public var body: some View {
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
                            viewStore.send(.tapFavorite(topic))
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
                            viewStore.send(.tapFavorite(feedContent))
                        }
                    )
                }
            }
            .separatorStyle(ThickSeparatorStyle())
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
            tag: content.item.media,
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
            tag: content.item.media,
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
public struct HomeListView_Previews: PreviewProvider {
    public static var previews: some View {
        HomeListView(
            store: .init(
                initialState: .init(
                    feedContents: [.videoMock(), .videoMock(), .videoMock()]
                ),
                reducer: .empty,
                environment: {}
            )
        )
        .background(Color.black)
        .previewLayout(.sizeThatFits)
        .environment(\.colorScheme, .dark)
        HomeListView(
            store: .init(
                initialState: .init(
                    feedContents: [.videoMock(), .videoMock(), .videoMock()]
                ),
                reducer: .empty,
                environment: {}
            )
        )
        .previewLayout(.sizeThatFits)
        .environment(\.colorScheme, .light)
    }
}
#endif
