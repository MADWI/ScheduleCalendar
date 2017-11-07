package pl.edu.zut.mad.schedule.util

import io.realm.RealmList
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.api.TimeRange
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import java.util.Date
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.api.Lesson as LessonApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi

internal class ModelMapper {

    companion object {
        const val CANCELED_LESSON_TEXT = "odwołane"
    }

    fun dayFromApiToUi(dayApi: DayApi): DayUi {
        val localDate = LocalDate.fromDateFields(dayApi.date)
        val lessonsUi = toUiLessonsFromApi(dayApi.lessons)
        return DayUi(localDate, lessonsUi)
    }

    private fun toUiLessonsFromApi(lessons: RealmList<LessonApi>): List<LessonUi> =
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

    fun toUiDate(date: Date?): LocalDate =
        if (date != null) {
            LocalDate.fromDateFields(date)
        } else {
            LocalDate.now()
        }

    fun toLessonEvent(emptyDay: EmptyDay) = LessonEvent(emptyDay.date, null)
}
