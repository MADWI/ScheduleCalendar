package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import org.joda.time.LocalDate

// TODO: create api and ui model
open class Day(date: LocalDate = LocalDate.now(),
               lessons: List<Lesson> = ArrayList()) : RealmObject() {

    private var time = date.toString()

    var lessons: RealmList<Lesson> = RealmList()
        private set

    @Ignore
    var date: LocalDate? = null
        get() = LocalDate.parse(time)
        private set

    init {
        this.lessons.addAll(lessons)
    }
}
