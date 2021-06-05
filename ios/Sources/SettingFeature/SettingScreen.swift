import Styleguide
import Component
import SwiftUI

public enum SettingModel: Hashable {
    case toggle(title: String, isOn: Bool)
}

public struct SettingScreen: View {

    @State private var items: [SettingModel]

    @Environment(\.presentationMode) var presentationMode

    public init(isDarkModeOn: Bool, isLaunguageOn: Bool) {
        let darkModeModel = SettingModel.toggle(
            title: L10n.SettingScreen.ListItem.darkMode,
            isOn: isDarkModeOn
        )
        let languageModel = SettingModel.toggle(
            title: L10n.SettingScreen.ListItem.language,
            isOn: isLaunguageOn
        )
        _items = State(initialValue: [darkModeModel, languageModel])
        
        UITableView.appearance().backgroundColor = UIColor(AssetColor.Background.primary.color)
    }

    public var body: some View {
        NavigationView {
            List {
                ForEach(items.indices) { index in
                    
                    if case SettingModel.toggle(let title, let isOn) = items[index] {
                        
                        let isOnBinding = Binding {
                            isOn
                        } set: { isOn in
                            items[index] = SettingModel.toggle(title: title, isOn: isOn)
                        }

                        SettingToggleItem(title: title, isOn: isOnBinding)
                    }
                }
            }
            .listStyle(PlainListStyle())
            .navigationBarTitle(L10n.SettingScreen.title, displayMode: .inline)
            .navigationBarItems(
                trailing: Button(action: {
                    presentationMode.wrappedValue.dismiss()
                }, label: {
                    Image(uiImage: AssetImage.iconClose.image)
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                })
            )
            .navigationBarColor(
                backgroundColor: AssetColor.Background.primary.color,
                titleColor: AssetColor.Base.primary.color
            )
        }
    }
}

struct SettingScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingScreen(
            isDarkModeOn: true,
            isLaunguageOn: false
        )
        SettingScreen(
            isDarkModeOn: true,
            isLaunguageOn: false
        )
        .colorScheme(.dark)
    }
}
