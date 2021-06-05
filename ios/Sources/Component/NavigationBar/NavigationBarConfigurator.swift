import Styleguide
import SwiftUI

public extension View {
    func navigationBarColor(backgroundColor: UIColor, titleColor: UIColor) -> some View {
        modifier(NavigationBarConfigurator(backgroundColor: backgroundColor, titleColor: titleColor))
    }
}

private struct NavigationBarConfigurator: ViewModifier {
    private let previousBackgroundColor: UIColor?
    private let previousTitleColor: UIColor?

    init(backgroundColor: UIColor, titleColor: UIColor) {
        previousTitleColor = UINavigationBar.appearance().standardAppearance.titleTextAttributes[.foregroundColor] as? UIColor
        previousBackgroundColor = UINavigationBar.appearance().standardAppearance.backgroundColor

        let appearance = UINavigationBarAppearance()
        appearance.configureWithTransparentBackground()

        appearance.titleTextAttributes = [.foregroundColor: titleColor]
        appearance.backgroundColor = backgroundColor
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
