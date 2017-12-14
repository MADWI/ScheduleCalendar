package pl.edu.zut.mad.schedule.animation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import pl.edu.zut.mad.schedule.R

class ColorAnimation(private @ColorRes val startColorId: Int,
    private @ColorRes val endColorId: Int) : Animation {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun start(view: View) {
        val startColor = ContextCompat.getColor(view.context, startColorId)
        val endColor = ContextCompat.getColor(view.context, endColorId)
        val time = view.context.resources.getInteger(R.integer.animation_time).toLong()
        with(ValueAnimator()) {
            setIntValues(startColor, endColor)
            setEvaluator(ArgbEvaluator())
            addUpdateListener { view.foreground = ColorDrawable(it.animatedValue as Int) }
            duration = time
            start()
        }
    }
}
