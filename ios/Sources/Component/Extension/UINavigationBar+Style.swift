import Styleguide
import UIKit

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
