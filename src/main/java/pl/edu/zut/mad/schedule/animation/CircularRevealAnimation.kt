package pl.edu.zut.mad.schedule.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils
import pl.edu.zut.mad.schedule.R

class CircularRevealAnimation(private val animationParams: AnimationParams,
    private val listener: (() -> Unit)? = null) : Animation {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun start(view: View) {
        val animator = with(animationParams) {
            ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
                startRadius.toFloat(), endRadius.toFloat())
        }
        animator.duration = view.context.resources.getInteger(R.integer.animation_time).toLong()
        animator.interpolator = FastOutSlowInInterpolator()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                listener?.invoke()
            }
        })
        animator.start()
    }
}
