import DroidKaigiMPP

internal struct DIContainer {
    static let shared: DIContainer = .init(authenticator: AuthenticatorMock())

    let koin: Koin_coreKoin

    init(authenticator: Authenticator) {
        let koinApplication = IosModuleKt.doInitKoin(authenticator: authenticator)
        self.koin = koinApplication.koin
    }

    func get<TypeProtocol, ReturnType>(type: TypeProtocol) -> ReturnType where TypeProtocol: Protocol {
        guard let object = koin.get(objCProtocol: type) as? ReturnType else { fatalError("Not found instance for \(type)") }
        return object
    }
}

internal class AuthenticatorMock: Authenticator {
    func currentUser(completionHandler: @escaping (User?, Error?) -> Void) {
    }

    func signInAnonymously(completionHandler: @escaping (User?, Error?) -> Void) {
    }
}
