package pl.edu.zut.mad.schedule.animation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View

@RequiresApi(Build.VERSION_CODES.M)
class ColorAnimation {

    fun startColorAnimation(view: View, startColor: Int, endColor: Int, animationDuration: Long) {
        with(ValueAnimator()) {
            setIntValues(startColor, endColor)
            setEvaluator(ArgbEvaluator())
            addUpdateListener { view.foreground = ColorDrawable(it.animatedValue as Int) }
            duration = animationDuration
            start()
        }
    }
}
