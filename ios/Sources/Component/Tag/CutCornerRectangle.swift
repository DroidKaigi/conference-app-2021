import SwiftUI

public struct CutCornerRectangle: Shape {
    private let targetCorners: [UIRectCorner]
    private let radius: CGFloat

    public init(
        targetCorners: [UIRectCorner],
        radius: CGFloat = 32
    ) {
        self.targetCorners = targetCorners
        self.radius = radius
    }

    func path(in rect: CGRect) -> Path {
        var path = Path()
        let topLeft = CGPoint(x: rect.minX, y: rect.minY)
        let topRight = CGPoint(x: rect.maxX, y: rect.minY)
        let bottomLeft = CGPoint(x: rect.minX, y: rect.maxY)
        let bottomRight = CGPoint(x: rect.maxX, y: rect.maxY)

        if targetCorners.contains(.allCorners) {
            path.move(to: CGPoint(x: topLeft.x, y: topLeft.y + radius))
            path.addLine(to: CGPoint(x: topLeft.x + radius, y: topLeft.y))
            path.addLine(to: CGPoint(x: topRight.x - radius, y: topRight.y))
            path.addLine(to: CGPoint(x: topRight.x, y: topRight.y + radius))
            path.addLine(to: CGPoint(x: bottomRight.x, y: bottomRight.y - radius))
            path.addLine(to: CGPoint(x: bottomRight.x - radius, y: bottomRight.y))
            path.addLine(to: CGPoint(x: bottomLeft.x + radius, y: bottomLeft.y))
            path.addLine(to: CGPoint(x: bottomLeft.x, y: bottomLeft.y - radius))
            path.move(to: CGPoint(x: topLeft.x, y: topLeft.y + radius))

            return path
        } else {
            if targetCorners.contains(.topLeft) {
                path.move(to: CGPoint(x: topLeft.x, y: topLeft.y + radius))
                path.addLine(to: CGPoint(x: topLeft.x + radius, y: topLeft.y))
            } else {
                path.move(to: topLeft)
            }

            if targetCorners.contains(.topRight) {
                path.addLine(to: CGPoint(x: topRight.x - radius, y: topRight.y))
                path.addLine(to: CGPoint(x: topRight.x, y: topRight.y + radius))
            } else {
                path.addLine(to: topRight)
            }

            if targetCorners.contains(.bottomRight) {
                path.addLine(to: CGPoint(x: bottomRight.x, y: bottomRight.y - radius))
                path.addLine(to: CGPoint(x: bottomRight.x - radius, y: bottomRight.y))
            } else {
                path.addLine(to: bottomRight)
            }

            if targetCorners.contains(.bottomLeft) {
                path.addLine(to: CGPoint(x: bottomLeft.x + radius, y: bottomLeft.y))
                path.addLine(to: CGPoint(x: bottomLeft.x, y: bottomLeft.y - radius))
            } else {
                path.addLine(to: bottomLeft)
            }
        }

        return path
    }
}

struct CutCornersRectangle_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle(targetCorners: [.allCorners]))

            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle(targetCorners: [.topLeft]))

            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle(targetCorners: [.topRight]))

            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle(targetCorners: [.bottomLeft]))

            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle(targetCorners: [.bottomRight]))

            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle(targetCorners: [.topLeft, .bottomRight]))

            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle(targetCorners: [.topRight, .bottomLeft]))
        }
        .previewLayout(.sizeThatFits)
    }
}
