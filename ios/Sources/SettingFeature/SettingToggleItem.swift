import Styleguide
import SwiftUI


struct SettingToggleItem: View {

    let title: String
    @Binding var isOn: Bool

    var body: some View {
        Toggle(isOn: $isOn, label: {
            Text(title)
        })
        .toggleStyle(SwitchToggleStyle(tint: Color(AssetColor.primary.color)))
        .listRowBackground(Color(AssetColor.Background.contents.color))
    }
}

struct SettingToggleItem_Previews: PreviewProvider {
    static var previews: some View {
        SettingToggleItem(title: "ダークモード", isOn: .constant(true))
    }
}
