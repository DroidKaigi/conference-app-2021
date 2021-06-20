import Algorithms
import Component
import SnapshotTesting
import XCTest

final class ImageViewTests: XCTestCase {
    func testImageView() {
        let placeholders = [PlaceHolder.noImage, PlaceHolder.noImagePodcast]
        let placeholderSizes = [PlaceHolder.Size.large, PlaceHolder.Size.medium, PlaceHolder.Size.small]
        product(placeholders, placeholderSizes).forEach { (placeholder, size) in
            let imageView = ImageView(imageURL: nil, placeholder: placeholder, placeholderSize: size)
            assertSnapshot(matching: imageView, as: .image)
        }
    }
}
