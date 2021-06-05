import Styleguide
import SwiftUI

public extension View {
    func navigationBarColor(backgroundColor: Color, titleColor: Color) -> some View {
        modifier(NavigationBarConfigurator(backgroundColor: backgroundColor, titleColor: titleColor))
    }
}

fileprivate struct NavigationBarConfigurator: ViewModifier {
    
    private let previousBackgroundColor: Color?
    private let previousTitleColor: Color?
    
    init(backgroundColor: Color, titleColor: Color) {
        
        if let previousTitleColor = UINavigationBar.appearance().standardAppearance.titleTextAttributes[.foregroundColor] as? UIColor {
            self.previousTitleColor = Color(previousTitleColor)
        } else {
            self.previousTitleColor = nil
        }
        
        if let previousBackgroundColor = UINavigationBar.appearance().standardAppearance.backgroundColor {
            self.previousBackgroundColor = Color(previousBackgroundColor)
        } else {
            self.previousBackgroundColor = nil
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
                
                if let previousBackgroundColor = previousBackgroundColor {
                    UINavigationBar.appearance().backgroundColor = UIColor(previousBackgroundColor)
                }
                
                if let previousTitleColor = previousTitleColor {
                    UINavigationBar.appearance().titleTextAttributes?[.foregroundColor] = previousTitleColor
                }
            })
    }
    
}