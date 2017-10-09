package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import org.joda.time.LocalDate

// TODO: change default date and check realm kotlin extension
open class Day(date: LocalDate = LocalDate.now(),
               lessons: List<Lesson> = ArrayList()) : RealmObject() {

    private var time = date.toString()

    @Ignore
    var date: LocalDate? = null
        get() = LocalDate.parse(time)
        private set

    private var lessons: RealmList<Lesson> = RealmList()
    fun getLessons() = lessons

    init {
        this.lessons.addAll(lessons)
    }
}
