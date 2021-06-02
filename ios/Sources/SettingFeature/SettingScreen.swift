import SwiftUI
import Styleguide

struct SettingToggleItem: Identifiable {
    let title: String
    var isOn: Bool
    
    var id: String {
        title
    }
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
            List {
                ForEach(toggleItems.indices) { index in
                    SettingToggleCell(settingToggleItem: $toggleItems[index])
                }
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
