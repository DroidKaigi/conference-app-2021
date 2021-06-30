package io.github.droidkaigi.feeder.feed

import androidx.annotation.FloatRange
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

private const val DURATION = 600
private val spectrumSize = 24.dp
private val barWidth = 3.dp
private val maxBarHeight = 12.dp

@Composable
fun AudioSpectrumAnimation(
    modifier: Modifier,
    isVisible: Boolean,
    isPlayingPodcast: Boolean,
    color: Color = MaterialTheme.colors.primary,
) {
    if (isVisible && isPlayingPodcast) {
        val offsetY = with(LocalDensity.current) { 6.dp.toPx() }

        val infiniteTransition = rememberInfiniteTransition()
        val leftBarFactor by infiniteTransition.animateHeight(
            0.5f, 0.1f, 0.5f, 0.3f, 0.6f
        )
        val centerBarFactor by infiniteTransition.animateHeight(
            0.4f, 0.65f, 0.35f, 0.6f, 0.45f
        )
        val rightBarFactor by infiniteTransition.animateHeight(
            0.6f, 0.2f, 0.6f, 0.35f, 0.4f
        )

        Canvas(modifier.size(spectrumSize)) {
            rotate(180f) {
                drawRect(
                    color = color,
                    topLeft = Offset(x = 6.dp.toPx(), y = offsetY),
                    size = Size(barWidth.toPx(), maxBarHeight.toPx() * rightBarFactor)
                )
                drawRect(
                    color = color,
                    topLeft = Offset(x = 10.dp.toPx(), y = offsetY),
                    size = Size(barWidth.toPx(), maxBarHeight.toPx() * centerBarFactor)
                )
                drawRect(
                    color = color,
                    topLeft = Offset(x = 14.dp.toPx(), y = offsetY),
                    size = Size(barWidth.toPx(), maxBarHeight.toPx() * leftBarFactor)
                )
            }
        }
    }
}

@Composable
private fun InfiniteTransition.animateHeight(
    @FloatRange(from = 0.1, to = 1.0) vararg factors: Float,
): State<Float> = animateFloat(
    initialValue = 0.5f,
    targetValue = 0.5f,
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            durationMillis = DURATION
            factors.forEachIndexed { index, factor ->
                factor at DURATION / (factors.size - 1) * index
            }
        },
        RepeatMode.Reverse
    )
)
