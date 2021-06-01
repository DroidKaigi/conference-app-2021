import Styleguide
import SwiftUI

public struct QuestionnaireView: View {
    public let onTapAnswer: () -> Void

    public init(onTapAnswer: @escaping () -> Void) {
        self.onTapAnswer = onTapAnswer
    }

    public var body: some View {
        VStack(alignment: .trailing, spacing: 12) {
            HStack {
                Image(uiImage: AssetImage.logo.image)
                Text("アンケートにご協力をお願いします")
                    .foregroundColor(Color(AssetColor.Base.primary.color))
                    .font(.headline)
                Spacer()
            }
            Button(
                action: onTapAnswer,
                label: {
                    Text("回答")
                        .foregroundColor(Color(AssetColor.primary.color))
                        .padding(.vertical, 8)
                        .padding(.horizontal, 32)
                        .overlay(
                            Rectangle()
                                .stroke(Color(AssetColor.primary.color))
                        )
                }
            )
        }
        .padding(.vertical, 16)
    }
}

public struct QuestionnaireView_Previews: PreviewProvider {
    public static var previews: some View {
        QuestionnaireView(onTapAnswer: {})
            .previewLayout(.sizeThatFits)
    }
}
