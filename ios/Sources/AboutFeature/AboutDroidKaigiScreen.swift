import SwiftUI
import Styleguide

public struct AboutDroidKaigiScreen: View {
    @Environment(\.presentationMode) var presentationMode

    public init() {
        UITableView.appearance().backgroundColor = .clear
        UINavigationBar.appearance().backgroundColor = .clear
        UINavigationBar.appearance().barTintColor = .clear
    }

    public var body: some View {
        NavigationView {
            VStack {
                VStack {
                    Text(L10n.AboutDroidKaigiScreen.whatIs).font(.system(size: 22))
                    AssetImage.logoTitle.image
                        .frame(width: 261.81, height: 41.06, alignment: .center)
                    Text(L10n.AboutDroidKaigiScreen.description)
                        .multilineTextAlignment(.center)
                        .font(.system(size: 17))
                        .opacity(0.7)
                }
                .padding(.horizontal, 32)

                List {
                    Button(action: {}, label: {
                        HStack() {
                            Text(L10n.AboutDroidKaigiScreen.behaviorCode)
                            Spacer()
                            Image(systemName: "book")
                        }
                    })
                    .listRowBackground(AssetColor.Background.contents.color)
                    .foregroundColor(AssetColor.Base.secondary.color)

                    Button(action: {}, label: {
                        HStack() {
                            Text(L10n.AboutDroidKaigiScreen.opensourceLincense)
                            Spacer()
                            Image(systemName: "star")
                        }
                    })
                    .listRowBackground(AssetColor.Background.contents.color)
                    .foregroundColor(AssetColor.Base.secondary.color)

                    Button(action: {}, label: {
                        HStack() {
                            Text(L10n.AboutDroidKaigiScreen.privacyPolicy)
                            Spacer()
                            Image(systemName: "magnifyingglass")
                        }
                    })
                    .listRowBackground(AssetColor.Background.contents.color)
                    .foregroundColor(AssetColor.Base.secondary.color)
                }
                .listStyle(InsetGroupedListStyle())

            }
            .background(AssetColor.Background.secondary.color.ignoresSafeArea())
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
