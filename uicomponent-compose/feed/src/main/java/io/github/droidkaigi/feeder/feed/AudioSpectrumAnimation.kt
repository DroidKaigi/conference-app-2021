package io.github.droidkaigi.feeder.feed

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

private const val DURATION = 500
private val barWidth = 3.dp
private val spacing = 1.dp

@Composable
fun AudioSpectrumAnimation(
    modifier: Modifier,
    isVisible: Boolean,
    isPlayingPodcast: Boolean,
    color: Color = MaterialTheme.colors.primary,
) {
    if (isVisible && isPlayingPodcast) {
        val offsetY = with(LocalDensity.current) {
            4.dp.toPx()
        }

        val infiniteTransition = rememberInfiniteTransition()
        val height1 by infiniteTransition.animateFloat(
            initialValue = 15f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = DURATION
                    15f at 0 with FastOutSlowInEasing
                    6f at (DURATION * 0.33).toInt() with FastOutSlowInEasing
                    11f at (DURATION * 0.66).toInt() with FastOutSlowInEasing
                    1f at DURATION with FastOutSlowInEasing
                },
                RepeatMode.Reverse
            )
        )
        val height2 by infiniteTransition.animateFloat(
            initialValue = 12f,
            targetValue = 6f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = DURATION
                    12f at 0 with FastOutSlowInEasing
                    6f at (DURATION * 0.33).toInt() with FastOutSlowInEasing
                    10f at (DURATION * 0.66).toInt() with FastOutSlowInEasing
                    6f at DURATION with FastOutSlowInEasing
                },
                RepeatMode.Reverse
            )
        )
        val height3 by infiniteTransition.animateFloat(
            initialValue = 7f,
            targetValue = 12f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = DURATION
                    2f at (DURATION * 0.33).toInt() with FastOutSlowInEasing
                    16f at (DURATION * 0.66).toInt() with FastOutSlowInEasing
                },
                RepeatMode.Reverse
            )
        )

        Canvas(modifier.size(24.dp)) {
            rotate(180f) {
                drawRect(
                    color = color,
                    topLeft = Offset(x = 10.dp.toPx(), y = offsetY),
                    size = Size(3.dp.toPx(), height1)
                )
                drawRect(
                    color = color,
                    topLeft = Offset(x = 10.dp.toPx(), y = offsetY),
                    size = Size(3.dp.toPx(), height2)
                )
                drawRect(
                    color = color,
                    topLeft = Offset(x = 14.dp.toPx(), y = offsetY),
                    size = Size(3.dp.toPx(), height3)
                )
            }
        }
    }
}
