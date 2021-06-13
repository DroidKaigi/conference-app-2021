import SwiftUI

public extension View {

    @ViewBuilder
    func `if`<Content: View>(_ condition: Bool, then: (Self) -> Content) -> some View {
        if condition {
            then(self)
        } else {
            self
        }
    }
}
