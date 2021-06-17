import Introspect
import SwiftUI
import Styleguide

public enum AboutDroidKaigiModel: CaseIterable {
    case behaviorCode
    case opensourceLicense
    case privacyPolicy

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

public struct AboutDroidKaigiScreen: View {
    @Environment(\.presentationMode) var presentationMode

    public init() {}

    public var body: some View {
        NavigationView {
            VStack {
                VStack(spacing: 8) {
                    Text(L10n.AboutDroidKaigiScreen.whatIs)
                        .font(.title2)
                    AssetImage.logoTitle.image
                        .frame(width: 262, height: 44)
                    Text(L10n.AboutDroidKaigiScreen.description)
                        .padding(.top, 16)
                        .multilineTextAlignment(.center)
                        .font(.body)
                        .opacity(0.7)
                }
                .padding(.horizontal, 32)

                List {
                    ForEach(AboutDroidKaigiModel.allCases, id: \.self) { model in
                        Button(action: {}, label: {
                            HStack {
                                Text(model.title)
                                    .font(.subheadline)
                                Spacer()
                                model.image
                            }
                        })
                        .listRowBackground(AssetColor.Background.contents.color)
                        .foregroundColor(AssetColor.Base.secondary.color)
                    }
                }
                .introspectTableView { tableView in
                    tableView.isScrollEnabled = false
                    tableView.backgroundColor = .clear
                }
                .listStyle(InsetGroupedListStyle())
            }
            .navigationBarTitleDisplayMode(.inline)
            .navigationBarItems(
                trailing: Button(action: {
                    presentationMode.wrappedValue.dismiss()
                }, label: {
                    AssetImage.iconClose.image
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                })
            )
            .introspectViewController { viewController in
                viewController.view.backgroundColor = AssetColor.Background.secondary.uiColor
            }
            .introspectNavigationController { navigationController in
                navigationController.navigationBar.backgroundColor = .clear
            }
        }
    }
}

struct AboutDroidKaigiScreen_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            AboutDroidKaigiScreen()
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, .dark)

            AboutDroidKaigiScreen()
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, .light)
        }
    }
}
