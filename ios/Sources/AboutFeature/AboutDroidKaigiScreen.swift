import Component
import ComposableArchitecture
import Introspect
import SwiftUI
import Styleguide

public struct AboutDroidKaigiScreen: View {
    @Environment(\.presentationMode) var presentationMode
    private let store: Store<AboutDroidKaigiState, AboutDroidKaigiAction>

    public init(store: Store<AboutDroidKaigiState, AboutDroidKaigiAction>) {
        self.store = store
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            VStack {
                HStack {
                    Spacer()

                    Button(action: {
                        presentationMode.wrappedValue.dismiss()
                    }, label: {
                        AssetImage.iconClose.image
                            .renderingMode(.template)
                            .foregroundColor(AssetColor.Base.primary.color)
                    })
                }
                .padding(.top, 31)
                .padding(.horizontal, 24)
                .padding(.bottom, 14)

                VStack(spacing: 24) {
                    VStack(spacing: 8) {
                        Text(L10n.AboutDroidKaigiScreen.whatIs)
                            .foregroundColor(AssetColor.Base.primary.color)
                            .font(.title2)

                        AssetImage.logoTitle.image
                            .resizable()
                            .frame(width: 196, height: 40)
                            .padding(.horizontal, 24)
                    }

                    Text(L10n.AboutDroidKaigiScreen.description)
                        .foregroundColor(AssetColor.Base.primary.color)
                        .multilineTextAlignment(.center)
                        .font(.body)
                        .opacity(0.7)
                }
                .padding(.horizontal, 32)

                List {
                    ForEach(AboutDroidKaigiModel.allCases, id: \.self) { model in
                        Button(action: {
                            viewStore.send(.tap(model))
                        }, label: {
                            HStack {
                                Text(model.title)
                                    .font(.subheadline)
                                    .foregroundColor(AssetColor.Base.primary.color)
                                Spacer()
                                model.image
                                    .foregroundColor(AssetColor.Base.secondary.color)
                            }
                        })
                    }
                    .listRowBackground(AssetColor.Background.secondary.color)
                }
                .introspectTableView { tableView in
                    tableView.isScrollEnabled = false
                    tableView.backgroundColor = AssetColor.Background.primary.uiColor
                }
                .listStyle(InsetGroupedListStyle())
            }
            .background(AssetColor.Background.primary.color.ignoresSafeArea())
            .sheet(
                isPresented: viewStore.binding(
                    get: \.isPresentingSheet,
                    send: .hideSheet
                )
            ) {
                WebView(url: viewStore.showingURL!)
            }
        }
    }
}

private extension AboutDroidKaigiModel {
    var title: String {
        switch self {
        case .behaviorCode:
            return L10n.AboutDroidKaigiScreen.behaviorCode
        case .opensourceLicense:
            return L10n.AboutDroidKaigiScreen.opensourceLincense
        case .privacyPolicy:
            return L10n.AboutDroidKaigiScreen.privacyPolicy
        }
    }

    var image: Image {
        switch self {
        case .behaviorCode:
            return Image(systemName: "book")
        case .opensourceLicense:
            return Image(systemName: "star")
        case .privacyPolicy:
            return Image(systemName: "magnifyingglass")
        }
    }
}

#if DEBUG
import UIApplicationClient

public struct AboutDroidKaigiScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            AboutDroidKaigiScreen(
                store: .init(
                    initialState: .init(),
                    reducer: .empty,
                    environment: AboutDroidKaigiEnvironment(
                        applicationClient: UIApplicationClientMock()
                    )
                )
            )
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, .dark)
            AboutDroidKaigiScreen(
                store: .init(
                    initialState: .init(),
                    reducer: .empty,
                    environment: AboutDroidKaigiEnvironment(
                        applicationClient: UIApplicationClientMock()
                    )
                )
            )
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, .light)
        }
    }
}
#endif
