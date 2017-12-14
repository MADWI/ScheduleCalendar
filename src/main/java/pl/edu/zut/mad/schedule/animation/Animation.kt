package pl.edu.zut.mad.schedule.animation

import android.view.View

interface Animation {

    fun start(view: View)

    interface Listener {

        fun onAnimationEnd()
    }

    interface Dimissible {

        fun dismiss()
    }
}
