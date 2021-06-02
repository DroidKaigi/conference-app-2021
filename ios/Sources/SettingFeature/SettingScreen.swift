import SwiftUI
import Styleguide

struct SettingToggleItem {
    let title: String
    var isOn: Bool
}

public struct SettingScreen: View {
    
    @State private var toggleItems: [SettingToggleItem] = [
        SettingToggleItem(title: "ダークモード", isOn: false),
        SettingToggleItem(title: "言語設定", isOn: true)
    ]
    
    @Environment(\.presentationMode) var presentationMode
    
    public init() { }
    
    public var body: some View {
        NavigationView {
            List(toggleItems.indices, id: \.self) { index in
                let title = toggleItems[index].title
                let toggleStatus = $toggleItems[index].isOn
                Toggle(isOn: toggleStatus, label: {
                    Text(title)
                })
                .toggleStyle(SwitchToggleStyle(tint: AssetColor.primary.color.color))
                .background(AssetColor.Background.contents.color.color)
            }
            .listStyle(PlainListStyle())
            .navigationBarTitle(L10n.SettingScreen.title, displayMode: .inline)
            .navigationBarItems(
                trailing: Button(action: {
                    presentationMode.wrappedValue.dismiss()
                }, label: {
                    Image(uiImage: AssetImage.iconClose.image)
                })
            )
        }
    }
}

// TODO: Move to other folder and remove `fileprivate` acces s modifier.
fileprivate extension UIColor {
    var color: Color {
        Color(self)
    }
}

struct SettingScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingScreen()
    }
}
