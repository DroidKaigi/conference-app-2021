import Combine
import DroidKaigiMPP

internal struct FlowWrapperPublisher<Output: AnyObject>: Publisher {
    typealias Output = Output
    typealias Failure = KotlinError

    private let flowWrapper: NonNullFlowWrapper<Output>
    private let scopeProvider: ScopeProvider

    init(
        flowWrapper: NonNullFlowWrapper<Output>,
        scopeProvider: ScopeProvider
    ) {
        self.flowWrapper = flowWrapper
        self.scopeProvider = scopeProvider
    }

    func receive<S>(subscriber: S) where S : Subscriber, Self.Failure == S.Failure, Self.Output == S.Input {
        flowWrapper.subscribe(scope: scopeProvider.scope) {
            let _ = subscriber.receive($0)
        } onComplete: {
            subscriber.receive(completion: .finished)
        } onFailure: {
            subscriber.receive(completion: .failure(KotlinError.fetchFailed($0.description())))
        }
    }
}

internal struct OptionalFlowWrapperPublisher<Output: AnyObject>: Publisher {
    typealias Output = Output?
    typealias Failure = KotlinError

    private let flowWrapper: NullableFlowWrapper<Output>
    private let scopeProvider: ScopeProvider

    init(
        flowWrapper: NullableFlowWrapper<Output>,
        scopeProvider: ScopeProvider
    ) {
        self.flowWrapper = flowWrapper
        self.scopeProvider = scopeProvider
    }

    func receive<S>(subscriber: S) where S: Subscriber, Self.Failure == S.Failure, Self.Output == S.Input {
        flowWrapper.subscribe(scope: scopeProvider.scope) {
            _ = subscriber.receive($0)
        } onComplete: {
            subscriber.receive(completion: .finished)
        } onFailure: {
            subscriber.receive(completion: .failure(KotlinError.fetchFailed($0.description())))
        }
    }
}
