import Foundation

public struct Staff: Identifiable, Equatable {
    // Todo: Change to real value if necessary
    public let id = UUID()
    public let name: String
    public let detail: String
    public let iconUrl: URL
}
