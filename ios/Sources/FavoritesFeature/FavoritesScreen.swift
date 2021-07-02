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
                        tapFavorite: { contentId in
                            viewStore.send(.favorite(contentId))
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

public struct FavoritesScreen_Previews: PreviewProvider {
    public static var previews: some View {
        FavoritesScreen(
            store: .init(
                initialState: .init(),
                reducer: favoritesReducer,
                environment: .init()
            )
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .light)

        FavoritesScreen(
            store: .init(
                initialState: .init(),
                reducer: favoritesReducer,
                environment: .init()
            )
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .dark)
    }
}
