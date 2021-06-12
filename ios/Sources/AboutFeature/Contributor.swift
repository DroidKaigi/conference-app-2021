import Foundation

public struct Contributor: Identifiable, Equatable {
    // TODO: Change to real value if necessary
    public let id = UUID()
    public let name: String
    public let iconUrl: URL
}
