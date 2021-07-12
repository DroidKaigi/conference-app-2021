import Repository
import UIApplicationClient

public struct AppEnvironment {
    public let contributorRepository: ContributorRepositoryProtocol
    public let deviceRepository: DeviceRepositoryProtocol
    public let feedRepository: FeedRepositoryProtocol
    public let staffRepository: StaffRepositoryProtocol
    public let themeRepository: ThemeRepositoryProtocol
    public let applicationClient: UIApplicationClientProtocol

    public init(
        contributorRepository: ContributorRepositoryProtocol,
        deviceRepository: DeviceRepositoryProtocol,
        feedRepository: FeedRepositoryProtocol,
        staffRepository: StaffRepositoryProtocol,
        themeRepository: ThemeRepositoryProtocol,
        applicationClient: UIApplicationClientProtocol
    ) {
        self.contributorRepository = contributorRepository
        self.deviceRepository = deviceRepository
        self.feedRepository = feedRepository
        self.staffRepository = staffRepository
        self.themeRepository = themeRepository
        self.applicationClient = applicationClient
    }
}

public extension AppEnvironment {
    static let shared: Self = {
        let authenticator = Authenticator()
        let container = DIContainer(authenticator: authenticator)

        return .init(
            contributorRepository: ContributorRepository(container: container),
            deviceRepository: DeviceRepository(container: container),
            feedRepository: FeedRepository(container: container),
            staffRepository: StaffRepository(container: container),
            themeRepository: ThemeRepository(container: container),
            applicationClient: UIApplicationClient()
        )
    }()

    static let noop: Self = {
        .init(
            contributorRepository: ContributorRepositoryMock(),
            deviceRepository: DeviceRepositoryMock(),
            feedRepository: FeedRepositoryMock(),
            staffRepository: StaffRepositoryMock(),
            themeRepository: ThemeRepositoryMock(),
            applicationClient: UIApplicationClientMock()
        )
    }()
}
