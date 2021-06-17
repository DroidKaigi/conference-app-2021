import SwiftUI
import Styleguide

public protocol SeparatorStyle {}

public struct ThickSeparatorStyle: SeparatorStyle {
    public init() {}
}

private struct SeparatorStyleEnvironmentKey: EnvironmentKey {
    static let defaultValue: SeparatorStyle? = nil
}

private extension EnvironmentValues {
    var separatorStyle: SeparatorStyle? {
        get { self[SeparatorStyleEnvironmentKey.self] }
        set { self[SeparatorStyleEnvironmentKey.self] = newValue }
    }
}

public struct Separator: View {

    @Environment(\.separatorStyle) private var separatorStyle: SeparatorStyle?

    public init() {}

    public var body: some View {
        let height: CGFloat
        switch separatorStyle {
        case is ThickSeparatorStyle:
            height = 1
        default:
            height = 0.5
        }
        return Rectangle()
            .foregroundColor(AssetColor.Separate.contents.color)
            .frame(height: height)
    }
}

public extension View {
    func separatorStyle<S: SeparatorStyle>(_ style: S) -> some View {
        self.environment(\.separatorStyle, style)
    }
}
