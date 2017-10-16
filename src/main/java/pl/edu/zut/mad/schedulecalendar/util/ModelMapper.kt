package pl.edu.zut.mad.schedulecalendar.util

import io.realm.RealmList
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedulecalendar.data.model.TimeRange
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day as DayDb
import pl.edu.zut.mad.schedulecalendar.data.model.db.Lesson as LessonDb
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Lesson as LessonUi


class ModelMapper {

    companion object {
        private val dateFormatter = DateTimeFormat.forPattern("dd-MM-yyyy")
    }

    fun toDateFromString(date: String): LocalDate = LocalDate.parse(date, dateFormatter)

    fun toStringFromDate(date: LocalDate): String = date.toString(dateFormatter)

    fun dayFromDbToUi(dayDb: DayDb): DayUi {
        val localDate = toDateFromString(dayDb.date)
        val lessonsUi = toUiLessonsFromDb(dayDb.lessons)
        return DayUi(localDate, lessonsUi)
    }

    private fun toUiLessonsFromDb(lessons: RealmList<LessonDb>): List<LessonUi> =
            lessons.map {
                with(it) {
                    val subjectWithCourseType = "$subject ($courseType)"
                    val teacherFullNameWithRoom = "${teacher?.academicTitle} ${teacher?.name} ${teacher?.surname} $room"
                    LessonUi(teacherFullNameWithRoom, subjectWithCourseType, timeRange ?: TimeRange())
                }
            }
}
