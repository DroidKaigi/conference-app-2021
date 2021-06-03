import Styleguide
import SwiftUI
import UIKit

public extension View {
    func navigationBarColor(backgroundColor: Color, titleColor: Color) -> some View {
        modifier(NavigationBarConfigurator(backgroundColor: backgroundColor, titleColor: titleColor))
    }
}

fileprivate struct NavigationBarConfigurator: ViewModifier {
    
    let newBackgroundColor: Color
    let newTitleColor: Color
    
    var previousBackgroundColor: UIColor
    var previousTitleColor: UIColor
    
    init(backgroundColor: Color, titleColor: Color) {
        
        self.newBackgroundColor = backgroundColor
        self.newTitleColor = titleColor
        
        if let previousTitleColor = UINavigationBar.appearance().titleTextAttributes?[.foregroundColor] as? UIColor {
            self.previousTitleColor = previousTitleColor
        } else {
            self.previousTitleColor = AssetColor.Base.primary.color
        }
        
        if let previousBackgroundColor = UINavigationBar.appearance().backgroundColor {
            self.previousBackgroundColor = previousBackgroundColor
        } else {
            self.previousBackgroundColor = AssetColor.Background.primary.color
        }
        
        
        let appearance = UINavigationBarAppearance()
        appearance.configureWithTransparentBackground()
        
        appearance.titleTextAttributes = [.foregroundColor: UIColor(titleColor)]
        appearance.backgroundColor = UIColor(backgroundColor)
        UINavigationBar.appearance().standardAppearance = appearance
    }
    
    func body(content: Content) -> some View {
        content
            .onDisappear(perform: {
                UINavigationBar.appearance().backgroundColor = previousBackgroundColor
                UINavigationBar.appearance().titleTextAttributes?[.foregroundColor] = previousTitleColor
            })
    }
    
}
