package pl.edu.zut.mad.schedule.util

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils
import java.io.Serializable

class AnimationUtils {

    companion object {
        fun registerCircularRevealAnimation(context: Context, view: View, revealSettings: RevealSettings, startColor: Int, endColor: Int) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return

            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    v.removeOnLayoutChangeListener(this)
                    val centerX = revealSettings.centerX
                    val centerY = revealSettings.centerY
                    val height = revealSettings.height
                    val width = revealSettings.width
//                    val duration = context.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                    val duration = 12000L //TODO remove
                    val finalRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat()
                    val animation = ViewAnimationUtils.createCircularReveal(v, centerX, centerY, 0F, finalRadius).setDuration(duration)
                    animation.interpolator = FastOutSlowInInterpolator()
                    animation.start()
                }
            })
        }
    }

    data class RevealSettings(val centerX: Int, val centerY: Int,
        val width: Int, val height: Int) : Serializable
}
