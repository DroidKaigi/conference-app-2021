import ComposableArchitecture
import Component
import Introspect
import Repository
import Model
import SwiftUI
import Styleguide

public struct SettingScreen: View {
    @Environment(\.presentationMode) var presentationMode

    private let store: Store<SettingState, SettingAction>

    public init(store: Store<SettingState, SettingAction>) {
        self.store = store
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            NavigationView {
                List {
                    Section(
                        header: Text(L10n.SettingScreen.ListItem.darkMode)
                    ) {
                        Text("Not Implemented!")
                    }
                    Section(
                        header: Text(L10n.SettingScreen.ListItem.language)
                    ) {
                        ForEach(Lang.allCases, id: \.self) { lang in
                            HStack {
                                Button {
                                    viewStore.send(.changeLanguage(lang))
                                } label: {
                                    Text(lang.type)
                                        .foregroundColor(AssetColor.Base.secondary.color)
                                }

                                Spacer()
                                if viewStore.language == lang {
                                    Image(systemName: "checkmark")
                                        .foregroundColor(AssetColor.primary.color)
                                }
                            }
                        }
                    }
                }
                .listStyle(InsetGroupedListStyle())
                .background(AssetColor.Background.primary.color.ignoresSafeArea())
                .navigationBarTitle(L10n.SettingScreen.title, displayMode: .inline)
                .navigationBarItems(
                    trailing: Button(action: {
//                        presentationMode.wrappedValue.dismiss()
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
