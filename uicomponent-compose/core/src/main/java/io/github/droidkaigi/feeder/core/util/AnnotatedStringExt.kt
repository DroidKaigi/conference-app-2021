package io.github.droidkaigi.feeder.core.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

fun createAutoLinkedAnnotateString(
    text: String,
    textColor: Color,
    linkColor: Color = Color.Blue,
): AnnotatedString {
    val regexp = Regex("""(https?://[^\s\t\n]+)|(`[^`]+`)|(@\w+)|(\*[\w]+\*)|(_[\w]+_)|(~[\w]+~)""")
    val annotatedString = buildAnnotatedString {
        val tokens = regexp.findAll(text)
        var cursorPosition = 0
        tokens.forEach { token ->
            withStyle(SpanStyle(color = textColor)) {
                append(text.slice(cursorPosition until token.range.first))
            }
            pushStringAnnotation(
                tag = "URL",
                annotation = token.value
            )
            withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                append(token.value)
            }
            pop()
            cursorPosition = token.range.last + 1
        }
        withStyle(SpanStyle(color = textColor)) {
            append(text.slice(cursorPosition until text.length))
        }
    }
    return annotatedString
}
