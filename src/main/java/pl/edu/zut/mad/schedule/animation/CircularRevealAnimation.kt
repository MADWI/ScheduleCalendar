package pl.edu.zut.mad.schedule.animation

import android.animation.Animator
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils
import pl.edu.zut.mad.schedule.R

class CircularRevealAnimation(private val animationParams: AnimationParams) : Animation {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun start(view: View) {
        val animation = getEnterAnimationWithSettings(view, animationParams)
        animation.duration = view.context.resources.getInteger(R.integer.animation_time).toLong()
        animation.interpolator = FastOutSlowInInterpolator()
        animation.start()
    }

    // TODO: remove method
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getEnterAnimationWithSettings(view: View, animationParams: AnimationParams): Animator {
        with(animationParams) {
            val finalRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat() // TODO move to AnimationParams
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius.toFloat(), finalRadius)
        }
    }
}
