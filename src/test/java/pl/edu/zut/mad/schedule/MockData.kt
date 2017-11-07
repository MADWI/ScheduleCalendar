package pl.edu.zut.mad.schedule

import com.tngtech.java.junit.dataprovider.DataProvider
import io.realm.RealmList
import pl.edu.zut.mad.schedule.data.model.api.Day
import pl.edu.zut.mad.schedule.data.model.api.Lesson
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
        private const val RESERVATION_STATUS_NOT_CANCELLED = "wykład"
        private const val RESERVATION_STATUS_CANCELLED = ModelMapper.CANCELED_LESSON_TEXT

        @JvmStatic
        @DataProvider
        fun dayApi(): Array<Array<Any>> {
            val lessons = RealmList<Lesson>()
            lessons.add(getLessonWithReservationStatus(RESERVATION_STATUS_NOT_CANCELLED))
            val day = Day(getDate(), lessons)
            return arrayOf(arrayOf<Any>(day))
        }

        @JvmStatic
        @DataProvider
        fun dayApiWithCancelledLesson(): Array<Array<Any>> {
            val lessons = RealmList<Lesson>()
            lessons.add(getLessonWithReservationStatus(RESERVATION_STATUS_CANCELLED))
            val day = Day(getDate(), lessons)
            return arrayOf(arrayOf<Any>(day))
        }

        private fun getDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.set(DATE_YEAR, DATE_MONTH - 1, DATE_DAY)
            return calendar.time
        }

        private fun getLessonWithReservationStatus(reservationStatus: String): Lesson {
            val teacher = Teacher(ACADEMIC_TITLE, TEACHER_NAME, TEACHER_SURNAME)
            val timeRange = TimeRange(TIME_START, TIME_END)
            return Lesson(SUBJECT, TYPE, ROOM, teacher, reservationStatus, timeRange)
        }
    }
}
