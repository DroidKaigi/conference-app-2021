import ComposableArchitecture
import Model
import Repository

public enum TimetableState: Equatable {
    case needToInitialize
    case initialized(TimetableLoadedState)
    case errorOccurred

    public init() {
        self = .needToInitialize
    }
}

public enum TimetableAction {
    case refresh
    case refreshResponse(Result<[AnyTimetableItem], KotlinError>)
    case loaded(TimetableLoadedAction)
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
        state: /TimetableState.initialized,
        action: /TimetableAction.loaded,
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
            state = .initialized(
                TimetableLoadedState(
                    timetableItems: items.sorted { $0.startsAt < $1.startsAt }
                )
            )
            return .none
        case let .refreshResponse(.failure(error)):
            state = .errorOccurred
            return .none
        case .loaded:
            return .none
        }
    }
)
