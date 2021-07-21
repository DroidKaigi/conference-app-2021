import ComposableArchitecture
import Component
import Introspect
import Repository
import Model
import SwiftUI
import Styleguide

public struct FavoritesScreen: View {
    private let store: Store<FavoritesState, FavoritesAction>

    public init(store: Store<FavoritesState, FavoritesAction>) {
        self.store = store
    }

    public var body: some View {
        NavigationView {
            ZStack {
                AssetColor.Background.primary.color.ignoresSafeArea()
                WithViewStore(store) { viewStore in
                    if viewStore.feedContents.isEmpty {
                        Text("表示するコンテンツがありません")
                    } else {
                        ScrollView {
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
            .navigationBarTitle(L10n.FavoriteScreen.title, displayMode: .large)
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

#if DEBUG
 public struct FavoritesScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            FavoritesScreen(
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
