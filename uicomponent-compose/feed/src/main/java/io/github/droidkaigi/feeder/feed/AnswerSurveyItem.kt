package io.github.droidkaigi.feeder.feed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme

@Composable
fun AnswerSurveyItem(
    modifier: Modifier = Modifier,
    surveyImage: String,
    surveyMessage: String,
    surveyButtonText: String,
    onClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (image, message, answer) = createRefs()

        NetworkImage(
            url = surveyImage,
            modifier = modifier
                .size(28.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, 24.dp)
                    bottom.linkTo(parent.bottom, 24.dp)
                    start.linkTo(parent.start, 24.dp)
                },
            contentScale = ContentScale.Fit,
            contentDescription = null
        )

        Text(
            text = surveyMessage,
            modifier = modifier
                .constrainAs(message) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(image.end, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
            },
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.End,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )

        Button(
            onClick = onClick,
            modifier = modifier
                .constrainAs(answer){
                    top.linkTo(message.bottom, 8.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(
                width = 1.5.dp,
                color = MaterialTheme.colors.primary
            ),
            colors = ButtonDefaults
                .buttonColors(
                   backgroundColor = Color.White
                )
        ) {
            Text(
                text = surveyButtonText,
                color = MaterialTheme.colors.primary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun AnswerSurveyItemPreview() {
    ConferenceAppFeederTheme {
        AnswerSurveyItem(
            surveyImage = "",
            surveyMessage = "アンケートにご協力お願いします",
            surveyButtonText = "回答する"
        )
    }
}
