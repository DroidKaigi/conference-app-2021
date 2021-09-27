import ComposableArchitecture
import Foundation
import Model

public struct TimetableDetailState: Equatable {
    public var timetable: AnyTimetableItem
    public var isSheetPresented: URL?

    var isShowingSheet: Bool {
        isSheetPresented != nil
    }

    public init(
        timetable: AnyTimetableItem,
        isSheetPresented: URL? = nil
    ) {
        self.timetable = timetable
        self.isSheetPresented = isSheetPresented
    }
}

public enum TimetableDetailAction {
    case tapLink(URL)
    case hideSheet
}

public let timetableDetailReducer = Reducer<TimetableDetailState, TimetableDetailAction, TimetableLoadedEnvironment> { state, action, _ in
    switch action {
    case .tapLink(let link):
        state.isSheetPresented = link
        return .none
    case .hideSheet:
        state.isSheetPresented = nil
        return .none
    }
}
