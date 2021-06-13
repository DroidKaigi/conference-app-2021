import AppFeature
import SwiftUI

@main
struct DroidKaigiApp: App {
    var body: some Scene {
        WindowGroup {
            AppScreen(
                store: .init(
                    initialState: .init(),
                    reducer: appReducer,
                    environment: .shared
                )
            )
        }
    }
}
