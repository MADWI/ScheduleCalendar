package pl.edu.zut.mad.schedule.animation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View

class ColorAnimation(private val view: View, private @ColorRes val startColorId: Int,
    private @ColorRes val endColorId: Int) {

    private val startColor: Int = ContextCompat.getColor(view.context, startColorId)
    private val endColor: Int = ContextCompat.getColor(view.context, endColorId)
    private val animationDuration = 400L

    @RequiresApi(Build.VERSION_CODES.M)
    fun startColorAnimation() {
        with(ValueAnimator()) {
            setIntValues(startColor, endColor)
            setEvaluator(ArgbEvaluator())
            addUpdateListener { view.foreground = ColorDrawable(it.animatedValue as Int) }
            duration = animationDuration
            start()
        }
    }
}
