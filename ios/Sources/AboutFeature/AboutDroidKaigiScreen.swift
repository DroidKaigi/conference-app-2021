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
        NavigationView {
            VStack {
                VStack(spacing: 24) {
                    VStack(spacing: 8) {
                        Text(L10n.AboutDroidKaigiScreen.whatIs)
                            .foregroundColor(AssetColor.Base.primary.color)
                            .font(.title2)

                        AssetImage.logoTitle.image
                            .resizable()
                            .aspectRatio(262/44, contentMode: .fit)
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
                    WithViewStore(store) { viewStore in
                        ForEach(viewStore.aboutModels, id: \.self) { model in
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
                        .listRowBackground(AssetColor.Background.contents.color)
                    }
                }
                .introspectTableView { tableView in
                    tableView.isScrollEnabled = false
                    tableView.backgroundColor = .clear
                }
                .listStyle(InsetGroupedListStyle())
            }
            .navigationBarTitleDisplayMode(.inline)
            .introspectNavigationController { navigationController in
                navigationController.navigationBar.isTranslucent = false
                navigationController.navigationBar.barTintColor = AssetColor.Background.secondary.uiColor
                navigationController.navigationBar.shadowImage = UIImage()
            }
            .introspectViewController { viewController in
                viewController.view.backgroundColor = AssetColor.Background.secondary.uiColor
            }
            .navigationBarItems(
                trailing: Button(action: {
                    presentationMode.wrappedValue.dismiss()
                }, label: {
                    AssetImage.iconClose.image
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                })
            )
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
public struct AboutDroidKaigiScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
                AboutDroidKaigiScreen(
                    store: .init(
                        initialState: .init(),
                        reducer: .empty,
                        environment: {}
                    )
                )
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)
            }
        }
    }
}
#endif
