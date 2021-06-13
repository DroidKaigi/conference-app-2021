import Component
import Introspect
import Styleguide
import SwiftUI

public enum SettingModel: Hashable {
    case darkMode(isOn: Bool)
    case language(isOn: Bool)

    var title: String {
        switch self {
        case .darkMode:
            return L10n.SettingScreen.ListItem.darkMode
        case .language:
            return L10n.SettingScreen.ListItem.language
        }
    }

    var isOn: Bool {
        switch self {
        case let .darkMode(isOn), let .language(isOn):
            return isOn
        }
    }

    mutating func update(isOn: Bool) {
        switch self {
        case .darkMode:
            self = .darkMode(isOn: isOn)
        case .language:
            self = .language(isOn: isOn)
        }
    }
}

public struct SettingScreen: View {

    @State private var items: [SettingModel]

    @Environment(\.presentationMode) var presentationMode

    public init(isDarkModeOn: Bool, isLanguageOn: Bool) {
        let darkModeModel = SettingModel.darkMode(isOn: isDarkModeOn)
        let languageModel = SettingModel.language(isOn: isLanguageOn)
        _items = State(initialValue: [darkModeModel, languageModel])
    }

    public var body: some View {
        NavigationView {
            InlineTitleNavigationBarScrollView {
                LazyVStack(spacing: 0) {
                    ForEach(items.indices) { index in
                        ZStack(alignment: .bottom) {
                            SettingToggleItem(
                                title: items[index].title,
                                isOn: Binding(get: {
                                    items[index].isOn
                                }, set: { isOn in
                                    items[index].update(isOn: isOn)
                                })
                            )
                            .frame(minHeight: 44)
                            Divider()
                        }
                        .padding(.horizontal, 16)
                        .background(AssetColor.Background.contents.color)
                    }
                }
                .padding(.top, 24)
            }
            .navigationBarTitle(L10n.SettingScreen.title, displayMode: .inline)
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
                viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
            }
        }
    }
}

struct SettingScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingScreen(
            isDarkModeOn: true,
            isLanguageOn: false
        )
        SettingScreen(
            isDarkModeOn: true,
            isLanguageOn: false
        )
        .colorScheme(.dark)
    }
}
