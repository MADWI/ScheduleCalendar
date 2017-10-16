package pl.edu.zut.mad.schedulecalendar

import io.realm.Realm
import io.realm.RealmList
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedulecalendar.model.Teacher
import pl.edu.zut.mad.schedulecalendar.model.TimeRange
import pl.edu.zut.mad.schedulecalendar.model.db.Lesson as LessonDb
import pl.edu.zut.mad.schedulecalendar.model.ui.Lesson as LessonUi
import pl.edu.zut.mad.schedulecalendar.model.db.Day as DayDb
import pl.edu.zut.mad.schedulecalendar.model.ui.Day as DayUi


class ScheduleRepository(private val database: Realm) {

    companion object {
        private val dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy")
    }

    // TODO: change to async
    fun saveSchedule(scheduleDays: List<DayDb>) {
        database.beginTransaction()
        database.copyToRealm(scheduleDays)
        database.commitTransaction()
    }

    // TODO: move mapping to presenter
    fun getScheduleDayDates(): List<LocalDate> =
            database.where(DayDb::class.java)
                    .findAll()
                    .map { mapDateFromString(it.date) }

    fun getDayByDate(date: LocalDate): DayUi? {
        // TODO change to async
        val dayDb = database.where(DayDb::class.java)
                .equalTo("date", date.toString(dateTimeFormatter))
                .findFirst() ?: return null
        val localDate = mapDateFromString(dayDb.date)
        val lessonsUi = mapLessons(dayDb.lessons)
        return DayUi(localDate, lessonsUi)
    }

    private fun mapDateFromString(date: String) = LocalDate.parse(date, dateTimeFormatter)

    // TODO: move mapping to separate class
    private fun mapLessons(lessons: RealmList<LessonDb>): List<LessonUi> =
            lessons.map { LessonUi(mapTeacher(it.teacher) + " " + it.room, "${it.subject} (${it.courseType})", it.timeRange ?: TimeRange()) }

    private fun mapTeacher(teacher: Teacher?) = "${teacher?.academicTitle} ${teacher?.name} ${teacher?.surname}"
}
