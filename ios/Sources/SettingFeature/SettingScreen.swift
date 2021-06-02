import SwiftUI
import Styleguide

struct SettingToggleModel: Hashable {
    let title: String
    var isOn: Bool
}

public struct SettingScreen: View {

    @State private var items: [SettingToggleModel] = [
        SettingToggleModel(
            title: L10n.SettingScreen.ListItem.darkMode,
            isOn: false
        ),
        SettingToggleModel(
            title: L10n.SettingScreen.ListItem.language,
            isOn: true
        )
    ]

    @Environment(\.presentationMode) var presentationMode

    public init() { }

    public var body: some View {
        NavigationView {
            List {
                ForEach(items.indices) { index in
                    SettingToggleItem(
                        title: items[index].title,
                        isOn: $items[index].isOn
                    )
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
                        .foregroundColor(AssetColor.Base.primary.color.color)
                })
            )
        }
    }
}

// TODO: Move to other folder
extension UIColor {
    var color: Color {
        Color(self)
    }
}

struct SettingScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingScreen()
    }
}
