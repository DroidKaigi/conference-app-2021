import ComposableArchitecture
import Component
import Introspect
import SettingFeature
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
            WithViewStore(store) { viewStore in
                ScrollView {
                    LazyVGrid(
                        columns: Array(
                            repeating: GridItem(.flexible(), spacing: .zero),
                            count: 2
                        ),
                        spacing: 16
                    ) {
                        ForEach(viewStore.items) { item in
                            createCard(
                                item: item,
                                tapAction: {
                                    viewStore.send(.tap(item))
                                },
                                tapFavoritesAction: {
                                    viewStore.send(.favorite(item))
                                }
                            )
                            .padding(8)
                        }
                    }
                    .onAppear {
                        viewStore.send(.refresh)
                    }
                    .padding(.horizontal, 8)
                }
                .navigationBarTitle(L10n.FavoriteScreen.title, displayMode: .large)
                .navigationBarItems(
                    trailing: Button(action: {
                        viewStore.send(.showSettings)
                    })
                    {
                        AssetImage.iconSetting.image
                            .renderingMode(.template)
                            .foregroundColor(AssetColor.Base.primary.color)
                    }
                    .sheet(
                        isPresented: viewStore.binding(
                            get: \.isSettingPresented,
                            send: .hideSettings
                        ),
                        content: {
                            SettingScreen(isDarkModeOn: true, isLanguageOn: true)
                        }
                    )
                )
                .introspectViewController { viewController in
                    viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
                }
            }
        }
    }
}

extension FavoritesScreen {
    private func createCard(item: FavoriteItem, tapAction: @escaping () -> Void, tapFavoritesAction: @escaping () -> Void) -> some View {
        SmallCard(
            title: item.title,
            imageURL: item.imageURL,
            tag: item.tag,
            date: item.date,
            isFavorited: item.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoritesAction
        )
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
