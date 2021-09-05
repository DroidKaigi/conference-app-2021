import Component
import ComposableArchitecture
import Introspect
import Styleguide
import SwiftUI

public struct SettingScreen: View {

    @Environment(\.presentationMode) var presentationMode
    private let store: Store<SettingState, SettingAction>

    public init(store: Store<SettingState, SettingAction>) {
        self.store = store
    }

    public var body: some View {
        NavigationView {
            InlineTitleNavigationBarScrollView {
                LazyVStack(spacing: 0) {
                    WithViewStore(store) { viewStore in
                        ForEach(viewStore.items.indices) { index in
                            ZStack(alignment: .bottom) {
                                let item = viewStore.items[index]
                                SettingToggleItem(
                                    title: item.title,
                                    isOn: Binding(get: {
                                        item.isOn
                                    }, set: { isOn in
                                        viewStore.send(.toggle(item.update(isOn: isOn)))
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

    func update(isOn: Bool) -> Self {
        switch self {
        case .darkMode:
            return .darkMode(isOn)
        case .language:
            return .language(isOn)
        }
    }
}

#if DEBUG
public struct SettingScreen_Previews: PreviewProvider {
    public static var previews: some View {
        SettingScreen(
            store: .init(
                initialState: .init(items: [SettingModel.darkMode(true), SettingModel.language(false)]),
                reducer: settingReducer,
                environment: SettingEnvironment()
            )
        )
        .environment(\.colorScheme, .light)
        SettingScreen(
            store: .init(
                initialState: .init(items: [SettingModel.darkMode(false), SettingModel.language(true)]),
                reducer: settingReducer,
                environment: SettingEnvironment()
            )
        )
        .environment(\.colorScheme, .dark)
    }
}
#endif
