import Styleguide
import SwiftUI


public struct SettingToggleItem: View {
    
    private let title: String
    @Binding private var isOn: Bool
    
    public init(title: String, isOn: Binding<Bool>) {
        self.title = title
        self._isOn = isOn
    }
    
    public var body: some View {
        Toggle(isOn: $isOn, label: {
            Text(title)
        })
        .toggleStyle(SwitchToggleStyle(tint: AssetColor.primary.color))
        .listRowBackground(AssetColor.Background.contents.color)
    }
}

struct SettingToggleItem_Previews: PreviewProvider {
    static var previews: some View {
        SettingToggleItem(
            title: "ダークモード",
            isOn: .constant(true)
        )
        SettingToggleItem(
            title: "ダークモード",
            isOn: .constant(true)
        )
        .colorScheme(.dark)
    }
}
