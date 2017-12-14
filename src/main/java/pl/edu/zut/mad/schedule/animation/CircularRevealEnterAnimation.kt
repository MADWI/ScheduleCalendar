package pl.edu.zut.mad.schedule.animation

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils
import pl.edu.zut.mad.schedule.R

class CircularRevealEnterAnimation(private val animationParams: AnimationParams) : Animation {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun start(view: View) {
        val animation = with(animationParams) {
            ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
                startRadius.toFloat(), endRadius.toFloat())
        }
        animation.duration = view.context.resources.getInteger(R.integer.animation_time).toLong()
        animation.interpolator = FastOutSlowInInterpolator()
        animation.start()
    }
}
