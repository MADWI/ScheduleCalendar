package pl.edu.zut.mad.schedule.util

import io.realm.RealmList
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import pl.edu.zut.mad.schedule.data.model.ui.OptionalDay
import pl.edu.zut.mad.schedule.search.SearchInput
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
        const val EXAM_LESSON_TEXT = "egzamin"
        private const val QUERY_TEACHER_NAME = "name"
        private const val QUERY_TEACHER_SURNAME = "surname"
        private const val QUERY_FACULTY = "faculty"
        private const val QUERY_SUBJECT = "subject"
        private const val QUERY_FIELD_OF_STUDY = "fieldOfStudy"
        private const val QUERY_COURSE_TYPE = "courseType"
        private const val QUERY_SEMESTER = "semester"
        private const val QUERY_FORM = "form"
        private const val QUERY_ROOM = "room"
        private const val QUERY_DATE_FROM = "dateFrom"
        private const val QUERY_DATE_TO = "dateTo"
    }

    fun toUiLessons(days: List<DayApi>): List<LessonUi> =
        days.map { toDayUiFromApi(it) }
            .flatMap { it.lessons }

    fun toDayUiFromApi(dayApi: DayApi): DayUi {
        val date = LocalDate.fromDateFields(dayApi.date)
        val lessonsUi = toLessonsUiFromApi(dayApi.lessons, date)
        return DayUi(date, lessonsUi)
    }

    fun toLessonsSearchQueryMap(searchInput: SearchInput): Map<String, String> {
        val query = HashMap<String, String>()
        query[QUERY_TEACHER_NAME] = searchInput.name
        query[QUERY_TEACHER_SURNAME] = searchInput.surname
        query[QUERY_FACULTY] = searchInput.faculty
        query[QUERY_SUBJECT] = searchInput.subject
        query[QUERY_FIELD_OF_STUDY] = searchInput.fieldOfStudy
        query[QUERY_COURSE_TYPE] = searchInput.courseType
        query[QUERY_SEMESTER] = searchInput.semester
        query[QUERY_FORM] = searchInput.form
        query[QUERY_ROOM] = searchInput.room
        query[QUERY_DATE_FROM] = searchInput.dateFrom
        query[QUERY_DATE_TO] = searchInput.dateTo
        return query
    }

    fun toUiDate(date: Date?): LocalDate =
        if (date != null) {
            LocalDate.fromDateFields(date)
        } else {
            LocalDate.now()
        }

    fun toLessonsEvents(optionalDay: OptionalDay): List<LessonEvent> = when (optionalDay) {
        is DayUi -> toLessonsEventsFromDayUi(optionalDay)
        is EmptyDay -> listOf(toLessonEventFromEmptyDay(optionalDay))
    }

    private fun toLessonsUiFromApi(lessons: RealmList<LessonApi>, date: LocalDate): List<LessonUi> =
        lessons.map {
            with(it) {
                val isCancelled = reservationStatus.equals(CANCELED_LESSON_TEXT, true)
                val isExam = reservationStatus.equals(EXAM_LESSON_TEXT, true)
                val timeRangeUi = toUiTimeRange(timeRange ?: TimeRangeApi())
                val teacherUi = toUiTeacher(teacher ?: TeacherApi())
                LessonUi(subject, courseType, room, teacherUi, facultyAbbreviation,
                    fieldOfStudy, semester, isCancelled, isExam, timeRangeUi, date)
            }
        }

    private fun toUiTimeRange(timeRangeApi: TimeRangeApi) =
        with(timeRangeApi) { TimeRangeUi(from, to) }

    private fun toUiTeacher(teacherApi: TeacherApi) =
        with(teacherApi) { TeacherUi(academicTitle, name, surname) }

    private fun toLessonsEventsFromDayUi(day: DayUi): List<LessonEvent> =
        day.lessons
            .map { LessonEvent(day.date, it) }
            .toList()

    private fun toLessonEventFromEmptyDay(emptyDay: EmptyDay) = LessonEvent(emptyDay.date, null)
}
