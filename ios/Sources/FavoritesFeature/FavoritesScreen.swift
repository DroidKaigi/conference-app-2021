import ComposableArchitecture
import Component
import Introspect
import SwiftUI
import Styleguide

public struct FavoritesScreen: View {
    private let store: Store<FavoritesState, FavoritesAction>

    public init(
        store: Store<FavoritesState, FavoritesAction>
    ) {
        self.store = store
    }

    public var body: some View {
        NavigationView {
            ScrollView {
                WithViewStore(store) { viewStore in
                    FeedContentListView(
                        feedContents: viewStore.contents,
                        tapContent: { content in
                            viewStore.send(.tap(content))
                        },
                        tapFavorite: { isFavorited, contentId in
                            viewStore.send(.tapFavorite(isFavorited: isFavorited, id: contentId))
                        }
                    )
                    .onAppear {
                        viewStore.send(.refresh)
                    }
                }
                .padding(.horizontal, 8)
            }
            .navigationBarTitle(L10n.FavoriteScreen.title, displayMode: .large)
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

#if DEBUG
public struct FavoritesScreen_Previews: PreviewProvider {
    public static var previews: some View {
        FavoritesScreen(
            store: .init(
                initialState: .init(
                    contents: [
                        .blogMock(),
                        .blogMock(),
                        .videoMock(),
                        .videoMock(),
                        .podcastMock(),
                        .podcastMock()
                    ]
                ),
                reducer: favoritesReducer,
                environment: .init()
            )
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .light)

        FavoritesScreen(
            store: .init(
                initialState: .init(
                    contents: [
                        .blogMock(),
                        .blogMock(),
                        .videoMock(),
                        .videoMock(),
                        .podcastMock(),
                        .podcastMock()
                    ]
                ),
                reducer: favoritesReducer,
                environment: .init()
            )
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .dark)
    }
}
#endif
