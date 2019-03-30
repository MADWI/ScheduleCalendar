package pl.edu.zut.mad.schedule.util

import android.os.Build
import androidx.annotation.RequiresApi
import android.view.View
import pl.edu.zut.mad.schedule.animation.Animation

internal class Animations {

    companion object {
        fun registerStartAnimation(view: View, vararg animations: Animation) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return

            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    v.removeOnLayoutChangeListener(this)
                    animations.forEach { it.start(view) }
                }
            })
        }

        fun startAnimations(view: View, vararg animations: Animation) {
            animations.forEach { it.start(view) }
        }
    }
}
