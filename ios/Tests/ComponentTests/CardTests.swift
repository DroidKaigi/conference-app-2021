import Algorithms
import Component
import Model
import SnapshotTesting
import XCTest

final class CardTests: XCTestCase {
    func testLargeCard() {
        let title = "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト..."
        let imageURL: URL? = nil
        let date = Date(timeIntervalSince1970: 0)

        product(Media.allCases, [true, false]).forEach { (media, isFavorited) in
            let largeCard = LargeCard(title: title, imageURL: imageURL, tag: media, date: date, isFavorited: isFavorited, tapAction: {}, tapFavoriteAction: {})
                .frame(width: 375, height: 319)
                .environment(\.colorScheme, .light)

            assertSnapshot(matching: largeCard, as: .image)
        }
    }

    func testMediumCard() {
        let title = "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト..."
        let imageURL: URL? = nil
        let date = Date(timeIntervalSince1970: 0)

        product(Media.allCases, [true, false]).forEach { (media, isFavorited) in
            let mediumCard = MediumCard(title: title, imageURL: imageURL, tag: media, date: date, isFavorited: isFavorited, tapAction: {}, tapFavoriteAction: {})
                .frame(width: 257, height: 258)

            assertSnapshot(matching: mediumCard, as: .image)
        }
    }

    func testSmallCard() {
        let title = "タイトルタイトルタイトルタイトルタイタイトルタイトルタイトルタイトルタイト..."
        let imageURL: URL? = nil
        let date = Date(timeIntervalSince1970: 0)

        product(Media.allCases, [true, false]).forEach { (media, isFavorited) in
            let smallCard = SmallCard(title: title, imageURL: imageURL, tag: media, date: date, isFavorited: isFavorited, tapAction: {}, tapFavoriteAction: {})
                .frame(width: 179, height: 278)

            assertSnapshot(matching: smallCard, as: .image)
        }
    }
}
