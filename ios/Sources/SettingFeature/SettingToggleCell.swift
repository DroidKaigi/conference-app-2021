//
//  SwiftUIView.swift
//  
//
//  Created by Fumiya Tanaka on 2021/06/03.
//

import SwiftUI
import Styleguide

struct SettingToggleCell: View {
    
    @Binding var settingToggleItem: SettingToggleItem
    
    var body: some View {
        Toggle(isOn: $settingToggleItem.isOn, label: {
            Text(settingToggleItem.title)
        })
        .toggleStyle(SwitchToggleStyle(tint: AssetColor.primary.color.color))
        .listRowBackground(AssetColor.Background.contents.color.color)
    }
}

struct SwiftUIView_Previews: PreviewProvider {
    static var previews: some View {
        SettingToggleCell(
            settingToggleItem: .constant(
                SettingToggleItem(
                    title: "ダークモード",
                    isOn: false
                )
            )
        )
    }
}
