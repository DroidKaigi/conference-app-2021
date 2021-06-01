import SwiftUI

struct CutCornerRectangle: Shape {
    private let radius: CGFloat

    init(
        radius: CGFloat = 32
    ) {
        self.radius = radius
    }

    func path(in rect: CGRect) -> Path {
        var path = Path()
        let topLeft = CGPoint(x: rect.minX, y: rect.minY)
        let topRight = CGPoint(x: rect.maxX, y: rect.minY)
        let bottomLeft = CGPoint(x: rect.minX, y: rect.maxY)
        let bottomRight = CGPoint(x: rect.maxX, y: rect.maxY)

        path.move(to: CGPoint(x: topLeft.x, y: topLeft.y + radius))
        path.addLine(to: CGPoint(x: topLeft.x + radius, y: topLeft.y))
        path.addLine(to: topRight)
        path.addLine(to: CGPoint(x: bottomRight.x, y: bottomRight.y - radius))
        path.addLine(to: CGPoint(x: bottomRight.x - radius, y: bottomRight.y))
        path.addLine(to: bottomLeft)
        path.move(to: CGPoint(x: topLeft.x, y: topLeft.y + radius))

        return path
    }
}

struct CutCornersRectangle_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            Rectangle()
                .frame(width: 200, height: 100)
                .clipShape(CutCornerRectangle())
        }
        .previewLayout(.sizeThatFits)
    }
}
