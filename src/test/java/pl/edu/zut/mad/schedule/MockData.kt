package pl.edu.zut.mad.schedule

import com.tngtech.java.junit.dataprovider.DataProvider
import io.realm.RealmList
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.api.Lesson as LessonApi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi
import pl.edu.zut.mad.schedule.data.model.api.Teacher
import pl.edu.zut.mad.schedule.data.model.api.TimeRange
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
        const val ACADEMIC_TITLE = "dr hab.inż"
        const val TEACHER_NAME = "Marcin"
        const val TEACHER_SURNAME = "Korzeń"
        const val ROOM = "WI1-128"
        const val TIME_START = "8:15"
        const val TIME_END = "10:00"
        const val TEACHER_WITH_ROOM = "$ACADEMIC_TITLE $TEACHER_NAME $TEACHER_SURNAME $ROOM"
        const val SUBJECT_WITH_TYPE = "$SUBJECT ($TYPE)"
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

        private fun getApiDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.set(DATE_YEAR, DATE_MONTH - 1, DATE_DAY)
            return calendar.time
        }

        private fun getUiDate(): LocalDate {
            return LocalDate.fromDateFields(getApiDate())
        }

        private fun getLessonApiWithReservationStatus(reservationStatus: String): LessonApi {
            val teacher = Teacher(ACADEMIC_TITLE, TEACHER_NAME, TEACHER_SURNAME)
            val timeRange = TimeRange(TIME_START, TIME_END)
            return LessonApi(SUBJECT, TYPE, ROOM, teacher, reservationStatus, timeRange)
        }

        private fun getLessonUiWithCancellation(): LessonUi {
            val timeRange = TimeRange(TIME_START, TIME_END)
            return LessonUi(TEACHER_WITH_ROOM, SUBJECT_WITH_TYPE, false, timeRange)
        }
    }
}
