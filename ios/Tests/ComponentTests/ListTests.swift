import Algorithms
import Component
import Model
import SnapshotTesting
import XCTest

final class ListTests: XCTestCase {
    func testListItem() {
        let allMedia = Media.allCases
        let isFavoriteCases = [true, false]
        let users = [
            [],
            [URL(string: "http://example.com")!],
            Array(repeating: URL(string: "http://example.com")!, count: 10),
        ]
        product(product(allMedia, isFavoriteCases), users)
            .map { ($0.0, $0.1, $1) }
            .forEach { (media, isFavorited, users) in
                let listItem = ListItem(
                    title: "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイ...",
                    tag: media,
                    imageURL: nil,
                    users: users,
                    date: Date(timeIntervalSince1970: 0),
                    isFavorited: isFavorited,
                    tapFavoriteAction: {},
                    tapAction: {}
                )
                .frame(width: 343, height: 132)
                assertSnapshot(matching: listItem, as: .image)
            }
    }
}
