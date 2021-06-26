import Component
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
                AssetImage.logo.image
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
                            CutCornerRectangle(
                                targetCorners: [.topLeft, .bottomRight],
                                radius: 8
                            )
                            .stroke(AssetColor.primary.color, lineWidth: 2)
                        )
                }
            )
        }
        .padding(16)
    }
}

public struct QuestionnaireView_Previews: PreviewProvider {
    public static var previews: some View {
        QuestionnaireView(tapAnswerAction: {})
            .frame(width: 375, height: 100)
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .light)
        QuestionnaireView(tapAnswerAction: {})
            .frame(width: 375, height: 100)
            .previewLayout(.sizeThatFits)
            .environment(\.colorScheme, .dark)
    }
}
