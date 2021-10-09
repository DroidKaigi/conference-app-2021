import ComposableArchitecture
import Component
import Foundation
import Model

public struct TimetableDetailState: Equatable {
    public var timetable: AnyTimetableItem
    public var webViewState: WebViewState?

    var isSheetPresented: Bool {
        webViewState != nil
    }

    public init(
        timetable: AnyTimetableItem
    ) {
        self.timetable = timetable
    }
}

public enum TimetableDetailAction {
    case tapLink(URL)
    case hideSheet
}

public let timetableDetailReducer = Reducer<TimetableDetailState, TimetableDetailAction, Void> { state, action, _ in
    switch action {
    case .tapLink(let link):
        state.webViewState = .init(url: link)
        return .none
    case .hideSheet:
        state.webViewState = nil
        return .none
    }
}
