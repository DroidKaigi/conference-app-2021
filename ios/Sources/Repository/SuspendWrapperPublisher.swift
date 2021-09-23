import Combine
import DroidKaigiMPP

internal struct SuspendWrapperPublisher<Output: AnyObject>: Publisher {
    typealias Output = Output
    typealias Failure = KotlinError
    
    private let suspendWrapper: NonNullSuspendWrapper<Output>
    private let scopeProvider: ScopeProvider

    init(
        suspendWrapper: NonNullSuspendWrapper<Output>,
        scopeProvider: ScopeProvider
    ) {
        self.suspendWrapper = suspendWrapper
        self.scopeProvider = scopeProvider
    }
    
    func receive<S>(subscriber: S) where S : Subscriber, Self.Failure == S.Failure, Self.Output == S.Input {
        suspendWrapper.subscribe(scope: scopeProvider.scope) {
            let _ = subscriber.receive($0)
        } onFailure: {
            subscriber.receive(completion: .failure(KotlinError.fetchFailed($0.description())))
        }
    }
}

