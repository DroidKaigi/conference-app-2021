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
                        ZStack(alignment: .bottom) {
                            SettingToggleItem(
                                title: L10n.SettingScreen.ListItem.darkMode,
                                isOn: Binding(get: {
                                    viewStore.darkModeIsOn
                                }, set: { isOn in
                                    viewStore.send(.darkMode(isOn))
                                })
                            )
                            .frame(minHeight: 44)
                            Separator()
                        }
                        .padding(.horizontal, 16)
                        .background(AssetColor.Background.contents.color)

                        ZStack(alignment: .bottom) {
                            SettingToggleItem(
                                title: L10n.SettingScreen.ListItem.language,
                                isOn: Binding(get: {
                                    viewStore.languageIsOn
                                }, set: { isOn in
                                    viewStore.send(.language(isOn))
                                })
                            )
                            .frame(minHeight: 44)
                            Separator()
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

#if DEBUG
public struct SettingScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            SettingScreen(
                store: .init(
                    initialState: .init(darkModeIsOn: true, languageIsOn: false),
                    reducer: settingReducer,
                    environment: SettingEnvironment()
                )
            )
            .environment(\.colorScheme, colorScheme)
        }
    }
}
#endif
