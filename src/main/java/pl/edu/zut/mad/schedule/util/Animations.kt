package pl.edu.zut.mad.schedule.util

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import pl.edu.zut.mad.schedule.animation.CircularRevealAnimation
import pl.edu.zut.mad.schedule.animation.ColorAnimation

internal class Animations {

    companion object {
        fun registerAnimation(view: View, animation: CircularRevealAnimation, colorAnimation: ColorAnimation) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return

            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    v.removeOnLayoutChangeListener(this)
                    animation.startEnterAnimation()
                    colorAnimation.startColorAnimation()
                }
            })
        }
    }
}
