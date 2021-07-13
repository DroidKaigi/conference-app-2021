import Component
import ComposableArchitecture
import Model
import SwiftUI

public struct FavoritesListView: View {
    private let store: Store<FavoritesListState, FavoritesListAction>

    public init(store: Store<FavoritesListState, FavoritesListAction>) {
        self.store = store
    }

    public var body: some View {
        ScrollView {
            WithViewStore(store) { viewStore in
                FeedContentListView(
                    feedContents: viewStore.feedContents,
                    tapContent: { content in
                        viewStore.send(.tap(content))
                    },
                    tapFavorite: { isFavorited, contentId in
                        viewStore.send(.tapFavorite(isFavorited: isFavorited, id: contentId))
                    }
                )
            }
        }
    }
}

#if DEBUG
public struct FavoritesListView_Previews: PreviewProvider {
    public static var previews: some View {
        FavoritesListView(
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

        FavoritesListView(
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
