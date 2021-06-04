import Styleguide
import SwiftUI

public struct QuestionnaireView: View {
    private let tapAnswerAction: () -> Void

    public init(tapAnswerAction: @escaping () -> Void) {
        self.tapAnswerAction = tapAnswerAction
    }

    public var body: some View {
        VStack(alignment: .trailing, spacing: 12) {
            HStack {
                Image(uiImage: AssetImage.logo.image)
                Text(L10n.HomeScreen.Questionnaire.title)
                    .foregroundColor(AssetColor.Base.primary.color)
                    .font(.headline)
                Spacer()
            }
            Button(
                action: tapAnswerAction,
                label: {
                    Text(L10n.HomeScreen.Questionnaire.answer)
                        .foregroundColor(AssetColor.primary.color)
                        .padding(.vertical, 8)
                        .padding(.horizontal, 32)
                        .overlay(
                            Rectangle()
                                .stroke(AssetColor.primary.color)
                        )
                }
            )
        }
        .padding(.vertical, 16)
    }
}

struct QuestionnaireView_Previews: PreviewProvider {
    static var previews: some View {
        QuestionnaireView(tapAnswerAction: {})
            .previewLayout(.sizeThatFits)
    }
}
