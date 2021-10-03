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
                    get: \.isShowingSheet,
                    send: .hideSheet
                ), content: {
                    IfLetStore(
                        store.scope(
                            state: \.settingState,
                            action: AppTabAction.setting
                        ),
                        then: SettingScreen.init(store:)
                    )
                    IfLetStore(store.scope(state: \.showingURL)) { sheetStore in
                        WithViewStore(sheetStore) { viewStore in
                            WebView(url: viewStore.state)
                        }
                    }
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
        case .timetable:
            return L10n.TimetableScreen.title
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
        case .timetable:
            return AssetImage.iconTimetable.image
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
    var isShowingSheet: Bool {
        showingURL != nil || settingState != nil
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
                        ], language: .en
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
