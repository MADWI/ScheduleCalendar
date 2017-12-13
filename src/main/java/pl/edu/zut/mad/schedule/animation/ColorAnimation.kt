package pl.edu.zut.mad.schedule.animation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View

class ColorAnimation {

    fun startColorAnimation(view: View, startColor: Int, endColor: Int, animationDuration: Long) {
        with(ValueAnimator()) {
            setIntValues(startColor, endColor)
            setEvaluator(ArgbEvaluator())
            addUpdateListener { view.setBackgroundColor(it.animatedValue as Int) }
            duration = animationDuration
            start()
        }
    }
}
