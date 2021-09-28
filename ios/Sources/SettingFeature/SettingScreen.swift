import ComposableArchitecture
import Component
import Introspect
import Repository
import Model
import SwiftUI
import Styleguide

public struct SettingScreen: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var showingActionSheet = false

    private let store: Store<SettingState, SettingAction>

    public init(store: Store<SettingState, SettingAction>) {
        self.store = store
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            NavigationView {
                InlineTitleNavigationBarScrollView {
                    LazyVStack(spacing: 0) {
                        ZStack(alignment: .bottom) {
                            SettingToggleItem(
                                title: L10n.SettingScreen.ListItem.darkMode,
                                isOn: Binding(get: {
                                    true
                                }, set: { isOn in
                                    print(isOn)
                                    // TODO: add darkMode setting implementation
                                })
                            )
                            .frame(minHeight: 44)
                            Separator()
                        }
                        .padding(.horizontal, 16)
                        .background(AssetColor.Background.contents.color)

                        Button {
                            showingActionSheet = true
                        } label: {
                            ZStack(alignment: .bottom) {
                                HStack {
                                    Text(L10n.SettingScreen.ListItem.language)
                                    Spacer()
                                    Text(viewStore.language.type).font(.caption)
                                }
                                .frame(minHeight: 44)
                                Separator()
                            }
                            .padding(.horizontal, 16)
                            .background(AssetColor.Background.contents.color)
                        }
                        .actionSheet(isPresented: $showingActionSheet) {
                            ActionSheet(
                                title: Text(L10n.SettingScreen.ListItem.language),
                                buttons: [
                                    .default(Text(L10n.SettingScreen.ListItem.LanguageType.system)) {
                                        viewStore.send(.changeLanguage(.system))
                                    },
                                    .default(Text(L10n.SettingScreen.ListItem.LanguageType.japanese)) {
                                        viewStore.send(.changeLanguage(.ja))
                                    },
                                    .default(Text(L10n.SettingScreen.ListItem.LanguageType.english)) {
                                        viewStore.send(.changeLanguage(.en))
                                    },
                                    .cancel()
                                ]
                            )
                        }
                    }
                    .padding(.top, 24)
                }
                .background(AssetColor.Background.primary.color.ignoresSafeArea())
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
            }
        }
    }
}

public struct SettingScreen_Previews: PreviewProvider {
    public static var previews: some View {
        SettingScreen(
            store: .init(
                initialState: .init(language: .system),
                reducer: .empty,
                environment: {}
            )
        )
        .environment(\.colorScheme, .dark)
    }
}

private extension Lang {
    var type: String {
        switch self {
        case .system: return L10n.SettingScreen.ListItem.LanguageType.system
        case .ja: return L10n.SettingScreen.ListItem.LanguageType.japanese
        case .en: return L10n.SettingScreen.ListItem.LanguageType.english
        }
    }
}
