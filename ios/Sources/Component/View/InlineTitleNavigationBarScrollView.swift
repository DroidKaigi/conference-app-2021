import SwiftUI
import Introspect

private struct ContentOffsetPreferenceKey: PreferenceKey {
    static var defaultValue: CGFloat = 0

    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {}
}

/**
 A vertical scroll view applies scrollEdgeAppearance when its content reaches the matching edge of the navigation bar.

 This behavior is similar to the default behavior from iOS 15 SDK, so can be removed after migrating to Xcode 13.
 */
public struct InlineTitleNavigationBarScrollView<Content: View>: View {

    public var content: Content
    public var showsIndicators: Bool
    @State private var shouldApplyScrollEdgeAppearance = true

    public init(
        showsIndicators: Bool = true,
        @ViewBuilder content: () -> Content
    ) {
        self.showsIndicators = showsIndicators
        self.content = content()
    }

    public var body: some View {
        _ = shouldApplyScrollEdgeAppearance
        // Reference: https://medium.com/@maxnatchanon/swiftui-how-to-get-content-offset-from-scrollview-5ce1f84603ec
        return GeometryReader { outsideProxy in
            ScrollView(showsIndicators: showsIndicators) {
                ZStack {
                    GeometryReader { insideProxy in
                        Color.clear
                            .preference(
                                key: ContentOffsetPreferenceKey.self,
                                value: outsideProxy.frame(in: .global).minY - insideProxy.frame(in: .global).minY
                            )
                    }
                    content
                }
            }
            .onPreferenceChange(ContentOffsetPreferenceKey.self) { value in
                shouldApplyScrollEdgeAppearance = value <= 0
            }
            .introspectViewController { viewController in
                guard let navigationBar = viewController.navigationController?.navigationBar else { return }
                let navigationItem = viewController.navigationItem
                navigationItem.standardAppearance = shouldApplyScrollEdgeAppearance
                    ? navigationBar.scrollEdgeAppearance
                    : navigationBar.standardAppearance
                navigationBar.layoutIfNeeded()
            }
        }
    }
}
