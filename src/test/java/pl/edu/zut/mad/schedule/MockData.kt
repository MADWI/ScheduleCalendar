package pl.edu.zut.mad.schedule

import com.tngtech.java.junit.dataprovider.DataProvider
import io.realm.RealmList
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.api.Lesson as LessonApi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi
import pl.edu.zut.mad.schedule.data.model.api.Teacher as TeacherApi
import pl.edu.zut.mad.schedule.data.model.ui.Teacher as TeacherUi
import pl.edu.zut.mad.schedule.data.model.api.TimeRange as TimeRangeApi
import pl.edu.zut.mad.schedule.data.model.ui.TimeRange as TimeRangeUi
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.util.ModelMapper
import java.util.Calendar
import java.util.Date

internal class MockData {

    companion object {
        const val DATE_YEAR = 2017
        const val DATE_MONTH = 11
        const val DATE_DAY = 7
        const val SUBJECT = "Algorytmy eksploracji danych"
        const val TYPE = "wykład"
        const val TEACHER_ACADEMIC_TITLE = "dr hab.inż"
        const val TEACHER_NAME = "Marcin"
        const val TEACHER_SURNAME = "Korzeń"
        const val ROOM = "WI1-128"
        const val TIME_START = "8:15"
        const val TIME_END = "10:00"
        const val TEACHER_WITH_ROOM = "$TEACHER_ACADEMIC_TITLE $TEACHER_NAME $TEACHER_SURNAME $ROOM"
        const val SUBJECT_WITH_TYPE = "$SUBJECT ($TYPE)"
        private val TEACHER_UI = TeacherUi(TEACHER_ACADEMIC_TITLE, TEACHER_NAME, TEACHER_SURNAME)
        private val TEACHER_API = TeacherApi(TEACHER_ACADEMIC_TITLE, TEACHER_NAME, TEACHER_SURNAME)
        private val TIME_RANGE_UI = TimeRangeUi(TIME_START, TIME_END)
        private val TIME_RANGE_API = TimeRangeApi(TIME_START, TIME_END)
        private const val RESERVATION_STATUS_NOT_CANCELLED = "wykład"
        private const val RESERVATION_STATUS_CANCELLED = ModelMapper.CANCELED_LESSON_TEXT

        @JvmStatic
        @DataProvider
        fun dayApi(): Array<Array<Any>> {
            val lessons = RealmList<LessonApi>()
            lessons.add(getLessonApiWithReservationStatus(RESERVATION_STATUS_NOT_CANCELLED))
            val day = DayApi(getApiDate(), lessons)
            return arrayOf(arrayOf<Any>(day))
        }

        @JvmStatic
        @DataProvider
        fun dayApiList(): Array<Array<Any>> {
            val lessons = RealmList<LessonApi>()
            lessons.add(getLessonApiWithReservationStatus(RESERVATION_STATUS_NOT_CANCELLED))
            val day = DayApi(getApiDate(), lessons)
            return arrayOf(arrayOf<Any>(listOf(day)))
        }

        @JvmStatic
        @DataProvider
        fun dayApiWithCancelledLesson(): Array<Array<Any>> {
            val lessons = RealmList<LessonApi>()
            lessons.add(getLessonApiWithReservationStatus(RESERVATION_STATUS_CANCELLED))
            val day = DayApi(getApiDate(), lessons)
            return arrayOf(arrayOf<Any>(day))
        }

        @JvmStatic
        @DataProvider
        fun dayUi(): Array<Array<Any>> {
            val lessons = ArrayList<LessonUi>()
            lessons.add(getLessonUiWithCancellation())
            val day = DayUi(getUiDate(), lessons)
            return arrayOf(arrayOf<Any>(day))
        }

        @JvmStatic
        @DataProvider
        fun dayApiAndUi(): Array<Array<Any>> {
            val dayUi = dayUi()[0][0]
            val dayApi = dayApi()[0][0]
            return arrayOf(arrayOf(dayApi, dayUi))
        }

        @JvmStatic
        @DataProvider
        fun emptyDay(): Array<Array<Any>> = arrayOf(arrayOf<Any>(EmptyDay(getUiDate())))

        private fun getApiDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.set(DATE_YEAR, DATE_MONTH - 1, DATE_DAY)
            return calendar.time
        }

        private fun getUiDate(): LocalDate = LocalDate.fromDateFields(getApiDate())

        private fun getLessonApiWithReservationStatus(reservationStatus: String): LessonApi {
            return LessonApi(SUBJECT, TYPE, ROOM, TEACHER_API, reservationStatus, TIME_RANGE_API)
        }

        private fun getLessonUiWithCancellation(): LessonUi {
            val date = getUiDate()
            return LessonUi(SUBJECT, TYPE, ROOM, TEACHER_UI, false, TIME_RANGE_UI, date)
        }
    }
}
