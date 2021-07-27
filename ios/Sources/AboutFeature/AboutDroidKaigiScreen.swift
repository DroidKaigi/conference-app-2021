import Introspect
import SwiftUI
import Styleguide

private enum AboutDroidKaigiModel: CaseIterable {
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
                    ForEach(AboutDroidKaigiModel.allCases, id: \.self) { model in
                        Button(action: {}, label: {
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
                .introspectTableView { tableView in
                    tableView.isScrollEnabled = false
                    tableView.backgroundColor = .clear
                }
                .listStyle(InsetGroupedListStyle())
            }
            .background(AssetColor.Background.primary.color.ignoresSafeArea())
            .navigationBarTitleDisplayMode(.inline)
            .introspectNavigationController { navigationController in
                navigationController.navigationBar.barTintColor = AssetColor.Background.secondary.uiColor
                navigationController.navigationBar.isTranslucent = false
                navigationController.navigationBar.shadowImage = UIImage()
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

public struct AboutDroidKaigiScreen_Previews: PreviewProvider {
    public static var previews: some View {
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
