import Component
import ComposableArchitecture
import Introspect
import Styleguide
import SwiftUI

public struct SettingScreen: View {

    @Environment(\.presentationMode) var presentationMode
    private let store: Store<SettingState, SettingAction>

    public init(
        isDarkModeOn: Bool, isLanguageOn: Bool
    ) {
        let darkModeModel = SettingModel.darkMode(isOn: isDarkModeOn)
        let languageModel = SettingModel.language(isOn: isLanguageOn)
        self.store = .init(
            initialState: .init(items: [darkModeModel, languageModel]),
            reducer: settingReducer,
            environment: SettingEnvironment()
        )
    }

    public var body: some View {
        NavigationView {
            InlineTitleNavigationBarScrollView {
                LazyVStack(spacing: 0) {
                    WithViewStore(store) { viewStore in
                        let items = viewStore.items
                        ForEach(items.indices) { index in
                            ZStack(alignment: .bottom) {
                                SettingToggleItem(
                                    title: items[index].title,
                                    isOn: Binding(get: {
                                        items[index].isOn
                                    }, set: { isOn in
                                        viewStore.send(items[index].action(isOn: isOn))
                                    })
                                )
                                .frame(minHeight: 44)
                                Separator()
                            }
                            .padding(.horizontal, 16)
                            .background(AssetColor.Background.contents.color)
                        }
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

private extension SettingModel {
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

    func action(isOn: Bool) -> SettingAction {
        switch self {
        case .darkMode:
            return .darkMode(isOn: isOn)
        case .language:
            return .language(isOn: isOn)
        }
    }
}

#if DEBUG
public struct SettingScreen_Previews: PreviewProvider {
    public static var previews: some View {
        SettingScreen(
            isDarkModeOn: true,
            isLanguageOn: false
        )
        .environment(\.colorScheme, .light)
        SettingScreen(
            isDarkModeOn: true,
            isLanguageOn: false
        )
        .environment(\.colorScheme, .dark)
    }
}
#endif
