package pl.edu.zut.mad.schedule.animation

interface Dismissible {

    fun dismiss(listener: Listener)

    interface Listener {
        fun onDismissed()
    }
}
