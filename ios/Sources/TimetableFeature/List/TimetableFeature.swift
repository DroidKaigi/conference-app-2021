import ComposableArchitecture
import Model
import Repository

public struct TimetableState: Equatable {
    public var type: TimetableStateType = .needToInitialize
    public var loadedState: TimetableLoadedState
    public var language: Lang

    public init(type: TimetableStateType = .needToInitialize,
                timetableItems: [AnyTimetableItem] = [],
                language: Lang) {
        self.type = type
        self.loadedState = TimetableLoadedState(timetableItems: timetableItems, language: language)
        self.language = language
    }
}

public enum TimetableStateType {
    case needToInitialize
    case initialized
    case errorOccurred
}

public enum TimetableAction {
    case refresh
    case refreshResponse(Result<[AnyTimetableItem], KotlinError>)
    case showSetting
    case none

    init(action: TimetableLoadedAction) {
        switch action {
        case .selectedPicker, .content, .detail, .hideDetail:
            self = .none
        case .showSetting:
            self = .showSetting
        }
    }
}

public struct TimetableEnvironment {
    public let timetableRepository: TimetableRepositoryProtocol

    public init(
        timetableRepository: TimetableRepositoryProtocol
    ) {
        self.timetableRepository = timetableRepository
    }
}

public let timetableReducer = Reducer<TimetableState, TimetableAction, TimetableEnvironment>.combine(
    timetableLoadedReducer.pullback(
        state: \.loadedState,
        action: /TimetableAction.init(action:),
        environment: {_ in
            .init()
        }
    ),
    .init { state, action, environment in
        switch action {
        case .refresh:
            return environment.timetableRepository.timetableContents()
                .receive(on: DispatchQueue.main)
                .catchToEffect()
                .map(TimetableAction.refreshResponse)
        case let .refreshResponse(.success(items)):
            state.type = .initialized
            state.loadedState = TimetableLoadedState(
                timetableItems: items.sorted { $0.startsAt < $1.startsAt },
                language: state.language
            )
            return .none
        case let .refreshResponse(.failure(error)):
            state.type = .errorOccurred
            return .none
        case .showSetting:
            return .none
        case .none:
            return .none
        }
    }
)
