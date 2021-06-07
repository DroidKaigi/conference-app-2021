import Styleguide
import UIKit

extension UITabBar {

    public func configureWithDefaultStyle() {
        let standardAppearance = UITabBarAppearance()
        standardAppearance.configureWithDefaultBackground()
        let normalColor = AssetColor.Base.disable.uiColor
        [standardAppearance.stackedLayoutAppearance, standardAppearance.inlineLayoutAppearance, standardAppearance.compactInlineLayoutAppearance]
            .map(\.normal)
            .forEach {
                $0.iconColor = normalColor
                $0.titleTextAttributes = [.foregroundColor: normalColor]
            }
        self.standardAppearance = standardAppearance
    }
}

extension UINavigationBar {

    public func configureWithDefaultStyle() {
        let standardAppearance = UINavigationBarAppearance()
        standardAppearance.configureWithDefaultBackground()
        let scrollEdgeAppearance = UINavigationBarAppearance()
        scrollEdgeAppearance.configureWithTransparentBackground()
        let tintColor = AssetColor.Base.primary.uiColor
        let textAttributes = [NSAttributedString.Key.foregroundColor: tintColor]
        for appearance in [standardAppearance, scrollEdgeAppearance] {
            appearance.titleTextAttributes = textAttributes
            appearance.largeTitleTextAttributes = textAttributes
            let buttonAppearance = UIBarButtonItemAppearance(style: .plain)
            buttonAppearance.normal.titleTextAttributes = textAttributes
            appearance.buttonAppearance = buttonAppearance
        }
        self.standardAppearance = standardAppearance
        self.scrollEdgeAppearance = scrollEdgeAppearance
        self.tintColor = tintColor
    }
}
