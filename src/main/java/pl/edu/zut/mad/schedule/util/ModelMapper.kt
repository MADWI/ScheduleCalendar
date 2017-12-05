package pl.edu.zut.mad.schedule.util

import io.realm.RealmList
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import java.util.Date
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.api.Lesson as LessonApi
import pl.edu.zut.mad.schedule.data.model.api.Teacher as TeacherApi
import pl.edu.zut.mad.schedule.data.model.api.TimeRange as TimeRangeApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi
import pl.edu.zut.mad.schedule.data.model.ui.Teacher as TeacherUi
import pl.edu.zut.mad.schedule.data.model.ui.TimeRange as TimeRangeUi

internal class ModelMapper {

    companion object {
        const val CANCELED_LESSON_TEXT = "odwołane"
    }

    fun toUiLessons(days: List<DayApi>): List<LessonUi> =
        days.map { toDayUiFromApi(it) }
            .flatMap { it.lessons }

    fun toDayUiFromApi(dayApi: DayApi): DayUi {
        val date = LocalDate.fromDateFields(dayApi.date)
        val lessonsUi = toLessonsUiFromApi(dayApi.lessons, date)
        return DayUi(date, lessonsUi)
    }

    private fun toLessonsUiFromApi(lessons: RealmList<LessonApi>, date: LocalDate): List<LessonUi> =
        lessons.map {
            with(it) {
                val isCancelled = reservationStatus.equals(CANCELED_LESSON_TEXT, true)
                val timeRangeUi = toUiTimeRange(timeRange ?: TimeRangeApi())
                val teacherUi = toUiTeacher(teacher ?: TeacherApi())
                LessonUi(subject, courseType, room, teacherUi, isCancelled, timeRangeUi, date)
            }
        }

    private fun toUiTimeRange(timeRangeApi: TimeRangeApi) =
        with(timeRangeApi) { TimeRangeUi(from, to) }

    private fun toUiTeacher(teacherApi: TeacherApi) =
        with(teacherApi) { TeacherUi(academicTitle, name, surname) }

    fun toLessonsEventsFromDayUi(day: DayUi): List<LessonEvent> =
        day.lessons
            .map { LessonEvent(day.date, it) }
            .toList()

    fun toUiDate(date: Date?): LocalDate =
        if (date != null) {
            LocalDate.fromDateFields(date)
        } else {
            LocalDate.now()
        }

    fun toLessonEventFromEmptyDay(emptyDay: EmptyDay) = LessonEvent(emptyDay.date, null)
}
