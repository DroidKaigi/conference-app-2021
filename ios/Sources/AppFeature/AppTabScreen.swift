import ComposableArchitecture
import HomeFeature
import MediaFeature
import FavoritesFeature
import AboutFeature
import SettingFeature
import SwiftUI
import Model
import Styleguide
import Component

public struct AppTabScreen: View {
    @State var selection: Int = .zero
    public let store: Store<AppTabState, AppTabAction>

    public init(store: Store<AppTabState, AppTabAction>) {
        self.store = store
        UITabBar.appearance().configureWithDefaultStyle()
        UINavigationBar.appearance().configureWithDefaultStyle()
    }

    public enum ViewAction {
        case reload
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            TabView(
                selection: $selection,
                content: {
                    ForEach(Array(AppTab.allCases.enumerated()), id: \.offset) { (offset, tab) in
                        tab.view(store)
                            .tabItem {
                                tab.image.renderingMode(.template)
                                Text(tab.title)
                            }
                            .tag(offset)
                    }
                }
            )
            .onReceive(
                NotificationCenter
                    .default
                    .publisher(for: UIApplication.willEnterForegroundNotification)
            ) { _ in
                viewStore.send(.reload)
            }
            .sheet(
                isPresented: viewStore.binding(
                    get: \.isShowingWebView,
                    send: .hideWebView
                ), content: {
                    WebView(url: viewStore.showingURL!)
                }
            )
            .sheet(
                isPresented: viewStore.binding(
                    get: \.isSettingPresented,
                    send: .hideSetting
                ),
                content: {
                    SettingScreen(isDarkModeOn: true, isLanguageOn: true)
                }
            )
        }
    }
}

private extension AppTab {
    var title: String {
        switch self {
        case .home:
            return L10n.HomeScreen.title
        case .media:
            return L10n.MediaScreen.title
        case .favorites:
            return L10n.FavoriteScreen.title
        case .about:
            return L10n.AboutScreen.title
        }
    }

    var image: SwiftUI.Image {
        switch self {
        case .home:
            return AssetImage.iconHome.image
        case .media:
            return AssetImage.iconBlog.image
        case .favorites:
            return AssetImage.iconStar.image
        case .about:
            return AssetImage.iconAbout.image
        }
    }
}

private extension AppTabState {
    var isShowingWebView: Bool {
        showingURL != nil
    }
}

#if DEBUG
 public struct AppTabScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            AppTabScreen(
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
