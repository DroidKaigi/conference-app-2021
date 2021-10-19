import Component
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
    case refreshResponse(Result<[AnyTimetableItem], KotlinError>)
    case loaded(TimetableLoadedAction)
    case loading(LoadingViewAciton)
    case error(ErrorViewAction)
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
        environment: { _ in
            .init()
        }
    ),
    loadingReducer.pullback(
        state: /TimetableState.needToInitialize,
        action: /TimetableAction.loading,
        environment: { _ in }
    ),
    errorViewReducer.pullback(
        state: /TimetableState.errorOccurred,
        action: /TimetableAction.error,
        environment: { _ in }
    ),
    .init { state, action, environment in
        switch action {
        case let .refreshResponse(.success(items)):
            state = .initialized(
                .init(timetableItems: items.sorted { $0.startsAt < $1.startsAt })
            )
            return .none
        case let .refreshResponse(.failure(error)):
            state = .errorOccurred
            return .none
        case .loaded:
            return .none
        case .loading(.onAppeared):
            return environment.timetableRepository.timetableContents()
                .receive(on: DispatchQueue.main)
                .catchToEffect()
                .map(TimetableAction.refreshResponse)
        case .error(.reload):
            state = .needToInitialize
            return .none
        }
    }
)
