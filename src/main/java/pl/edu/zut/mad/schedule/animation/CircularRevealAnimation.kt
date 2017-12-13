package pl.edu.zut.mad.schedule.animation

import android.animation.Animator
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils

internal class CircularRevealAnimation(private val view: View, private val animationParams: AnimationParams) {

    @RequiresApi(Build.VERSION_CODES.M)
    fun startEnterAnimation() {
        val animation = getEnterAnimationWithSettings(view, animationParams)
        val duration = 1400L //TODO extract and use resources
        animation.duration = duration
        animation.interpolator = FastOutSlowInInterpolator()
        animation.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getEnterAnimationWithSettings(view: View, animationParams: AnimationParams): Animator {
        with(animationParams) {
            val finalRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat() // TODO move to AnimationParams
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius.toFloat(), finalRadius)
        }
    }
}
