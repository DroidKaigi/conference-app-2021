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
    
    let previousBackgroundColor: Color
    let previousTitleColor: Color
    
    init(backgroundColor: Color, titleColor: Color) {
        
        self.newBackgroundColor = backgroundColor
        self.newTitleColor = titleColor
        
        if let previousTitleColor = UINavigationBar.appearance().standardAppearance.titleTextAttributes[.foregroundColor] as? UIColor {
            self.previousTitleColor = Color(previousTitleColor)
        } else {
            self.previousTitleColor = newTitleColor
        }
        
        if let previousBackgroundColor = UINavigationBar.appearance().standardAppearance.backgroundColor {
            self.previousBackgroundColor = Color(previousBackgroundColor)
        } else {
            self.previousBackgroundColor = newBackgroundColor
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
                UINavigationBar.appearance().backgroundColor = UIColor(previousBackgroundColor)
                UINavigationBar.appearance().titleTextAttributes?[.foregroundColor] = previousTitleColor
            })
    }
    
}
