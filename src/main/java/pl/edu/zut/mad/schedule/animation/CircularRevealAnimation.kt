package pl.edu.zut.mad.schedule.animation

import android.animation.Animator
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils

internal class CircularRevealAnimation(private val view: View, private val animationParams: AnimationParams,
    private @ColorRes val startColorId: Int, private @ColorRes val endColorId: Int) {

    private val startColor: Int = ContextCompat.getColor(view.context, startColorId)
    private val endColor: Int = ContextCompat.getColor(view.context, endColorId)

    @RequiresApi(Build.VERSION_CODES.M)
    fun startEnterAnimation() {
        val animation = getEnterAnimationWithSettings(view, animationParams)
        val duration = 400L // extract and use resources
        animation.duration = duration
        animation.interpolator = FastOutSlowInInterpolator()
        animation.start()
        ColorAnimation().startColorAnimation(view, startColor, endColor, duration) //TODO: clean up
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getEnterAnimationWithSettings(view: View, animationParams: AnimationParams): Animator {
        with(animationParams) {
            val finalRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat()
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius.toFloat(), finalRadius)
        }
    }
}
