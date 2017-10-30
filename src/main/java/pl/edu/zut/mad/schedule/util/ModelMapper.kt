package pl.edu.zut.mad.schedule.util

import io.realm.RealmList
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.db.TimeRange
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import java.util.*
import pl.edu.zut.mad.schedule.data.model.db.Day as DayDb
import pl.edu.zut.mad.schedule.data.model.db.Lesson as LessonDb
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi


class ModelMapper {

    companion object {
        private const val CANCELED_LESSON_TEXT = "odwołane"
    }

    fun dayFromDbToUi(dayDb: DayDb): DayUi {
        val localDate = LocalDate.fromDateFields(dayDb.date)
        val lessonsUi = toUiLessonsFromDb(dayDb.lessons)
        return DayUi(localDate, lessonsUi)
    }

    private fun toUiLessonsFromDb(lessons: RealmList<LessonDb>): List<LessonUi> =
            lessons.map {
                with(it) {
                    val subjectWithCourseType = "$subject ($courseType)"
                    val teacherFullNameWithRoom = "${teacher?.academicTitle} ${teacher?.name} ${teacher?.surname} $room"
                    val isCancelled = reservationStatus.equals(CANCELED_LESSON_TEXT, true)
                    LessonUi(teacherFullNameWithRoom, subjectWithCourseType, isCancelled, timeRange ?: TimeRange())
                }
            }

    fun toLessonsEvents(day: DayUi): List<LessonEvent> =
            day.lessons
                    .map { LessonEvent(day.date, it) }
                    .toList()

    fun toUiDate(date: Date?): LocalDate {
        return if (date != null) {
            LocalDate.fromDateFields(date)
        } else {
            LocalDate.now()
        }
    }
}
