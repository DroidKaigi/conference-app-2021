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
        Toggle(isOn: $isOn.animation(), label: {
            Text(title)
        })
        .toggleStyle(SwitchToggleStyle(tint: AssetColor.primary.color))
    }
}

public struct SettingToggleItem_Previews: PreviewProvider {
    public static var previews: some View {
        SettingToggleItem(
            title: "ダークモード",
            isOn: .constant(true)
        )
        .frame(width: 375, height: 44)
        .environment(\.colorScheme, .light)
        SettingToggleItem(
            title: "ダークモード",
            isOn: .constant(true)
        )
        .frame(width: 375, height: 44)
        .environment(\.colorScheme, .dark)
    }
}
