package pl.edu.zut.mad.schedule

internal interface ComponentView<out T> {

    fun getComponent(): T
}
