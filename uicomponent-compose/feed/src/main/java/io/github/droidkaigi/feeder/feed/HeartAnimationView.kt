package io.github.droidkaigi.feeder.feed

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation
import kotlin.random.Random

class HeartAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : View(context, attrs, defStyle) {

    private class Heart(
        var targetTransX: Float = 0f,
        var targetTransY: Float = 0f,
        var transX: Float = 0f,
        var transY: Float = 0f,
        var alpha: Float = 0f,
        var scale: Float = 0f,
    )

    private val drawable: Drawable =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)
            ?.apply {
                DrawableCompat.setTint(this, Color.RED)

                val halfWidth = intrinsicWidth / 2
                val halfHeight = intrinsicHeight / 2
                bounds = Rect(-halfWidth, -halfHeight, halfWidth, halfHeight)
            }
            ?: throw IllegalArgumentException()
    private val drawableHalfWidth = drawable.intrinsicWidth / 2
    private val drawableHalfHeight = drawable.intrinsicHeight / 2

    private val hearts = (1..10).map { Heart() }

    private val moveInterpolator = FastOutSlowInEasing
    private val alphaInterpolator = FastOutLinearInEasing
    private val scaleInterpolator = LinearOutSlowInEasing
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = DURATION
        interpolator = LinearInterpolator()
        addUpdateListener {
            if (isLaidOut) {
                val moveProgress = moveInterpolator.transform(it.animatedFraction)
                val alphaProgress = alphaInterpolator.transform(it.animatedFraction)
                val scaleProgress = scaleInterpolator.transform(it.animatedFraction)
                hearts.forEach { heart ->
                    heart.transX = lerp(0.5f, heart.targetTransX, moveProgress)
                    heart.transY = lerp(1f, heart.targetTransY, moveProgress)
                    heart.alpha = lerp(1f, 0f, alphaProgress)
                    heart.scale = lerp(1f, 0.6f, scaleProgress)
                }
                invalidate()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (animator.isRunning.not()) {
            return
        }
        hearts.forEach { heart ->
            val transX = lerp(drawableHalfWidth, width - drawableHalfWidth, heart.transX)
            val transY = lerp(drawableHalfHeight, height - drawableHalfHeight, heart.transY)
            canvas.withTranslation(transX, transY) {
                withScale(x = heart.scale, y = heart.scale, pivotX = 0.5f, pivotY = 0.5f) {
                    drawable.alpha = (255 * heart.alpha).toInt()
                    drawable.draw(canvas)
                }
            }
        }
    }

    fun execute() {
        if (animator.isRunning.not()) {
            hearts.forEach { heart ->
                heart.targetTransX = lerp(0f, 1f, Random.nextFloat())
                heart.targetTransY = lerp(0f, 2 / 3f, Random.nextFloat())
            }
            animator.start()
        }
    }

    companion object {
        private const val DURATION: Long = 800

        private fun lerp(from: Int, to: Int, progress: Float): Float {
            return (1 - progress) * from + to * progress
        }

        private fun lerp(from: Float, to: Float, progress: Float): Float {
            return (1 - progress) * from + to * progress
        }
    }
}
