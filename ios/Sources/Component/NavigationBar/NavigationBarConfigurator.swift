import Styleguide
import SwiftUI

public extension View {
    func navigationBarColor(backgroundColor: Color, titleColor: Color) -> some View {
        modifier(NavigationBarConfigurator(backgroundColor: backgroundColor, titleColor: titleColor))
    }
}

fileprivate struct NavigationBarConfigurator: ViewModifier {
    
    private let previousBackgroundColor: UIColor?
    private let previousTitleColor: UIColor?
    
    init(backgroundColor: Color, titleColor: Color) {
        
        previousTitleColor = UINavigationBar.appearance().standardAppearance.titleTextAttributes[.foregroundColor] as? UIColor
        previousBackgroundColor = UINavigationBar.appearance().standardAppearance.backgroundColor
        
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
                    UINavigationBar.appearance().backgroundColor = previousBackgroundColor
                }
                
                if let previousTitleColor = previousTitleColor {
                    UINavigationBar.appearance().titleTextAttributes?[.foregroundColor] = previousTitleColor
                }
            })
    }
    
}
