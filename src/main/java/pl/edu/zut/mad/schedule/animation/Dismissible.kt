package pl.edu.zut.mad.schedule.animation

interface Dismissible {

    fun dismiss(listener: () -> Unit)
}
