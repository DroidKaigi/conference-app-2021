import AppFeature
import SwiftUI

@main
struct DroidKaigiApp: App {
    var body: some Scene {
        WindowGroup {
            AppScreen(
                store: .init(
                    initialState: .init(type: .needToInitialize, language: .system),
                    reducer: appReducer,
                    environment: .shared
                )
            )
        }
    }
}
