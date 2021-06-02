import SwiftUI
import Styleguide

struct SettingToggleItem: View {

    let title: String
    @Binding var isOn: Bool

    var body: some View {
        Toggle(isOn: $isOn, label: {
            Text(title)
        })
        .toggleStyle(SwitchToggleStyle(tint: AssetColor.primary.color.color))
        .listRowBackground(AssetColor.Background.contents.color.color)
    }
}

struct SettingToggleItem_Previews: PreviewProvider {
    static var previews: some View {
        SettingToggleItem(title: "ダークモード", isOn: .constant(true))
    }
}
