import ComposableArchitecture
import Dispatch
import Player
import Repository

public struct AppEnvironment {
    public let contributorRepository: ContributorRepositoryProtocol
    public let deviceRepository: DeviceRepositoryProtocol
    public let feedRepository: FeedRepositoryProtocol
    public let staffRepository: StaffRepositoryProtocol
    public let themeRepository: ThemeRepositoryProtocol
    public let timetableRepository: TimetableRepositoryProtocol
    public let languageRepository: LanguageRepositoryProtocol
    public let mainQueue: AnySchedulerOf<DispatchQueue>
    public let player: PlayerProtocol

    public init(
        contributorRepository: ContributorRepositoryProtocol,
        deviceRepository: DeviceRepositoryProtocol,
        feedRepository: FeedRepositoryProtocol,
        staffRepository: StaffRepositoryProtocol,
        themeRepository: ThemeRepositoryProtocol,
        timetableRepository: TimetableRepositoryProtocol,
        languageRepository: LanguageRepositoryProtocol,
        player: PlayerProtocol
    ) {
        self.contributorRepository = contributorRepository
        self.deviceRepository = deviceRepository
        self.feedRepository = feedRepository
        self.staffRepository = staffRepository
        self.themeRepository = themeRepository
        self.timetableRepository = timetableRepository
        self.languageRepository = languageRepository
        self.mainQueue = .main
        self.player = player
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
            timetableRepository: TimetableRepository(container: container),
            languageRepository: LanguageRepository(container: container),
            player: Player()
        )
    }()

    static let noop: Self = {
        .init(
            contributorRepository: ContributorRepositoryMock(),
            deviceRepository: DeviceRepositoryMock(),
            feedRepository: FeedRepositoryMock(),
            staffRepository: StaffRepositoryMock(),
            themeRepository: ThemeRepositoryMock(),
            timetableRepository: TimetableRepositoryMock(),
            languageRepository: LanguageRepositoryMock(),
            player: PlayerMock()
        )
    }()
}
