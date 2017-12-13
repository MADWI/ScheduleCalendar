package pl.edu.zut.mad.schedule.animation

import android.animation.Animator
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils

internal class CircularRevealAnimation(private val view: View, private val animationSettings: AnimationSettings,
    private val startColor: Int, private val endColor: Int) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun startEnterAnimation() {
        val animation = getEnterAnimationWithSettings(view, animationSettings)
        val duration = 15000L // extract and use resources
        animation.duration = duration
        animation.interpolator = FastOutSlowInInterpolator()
        animation.start()
        ColorAnimation().startColorAnimation(view, startColor, endColor, duration)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getEnterAnimationWithSettings(view: View, animationSettings: AnimationSettings): Animator {
        with(animationSettings) {
            val finalRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat()
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius.toFloat(), finalRadius)
        }
    }
}
