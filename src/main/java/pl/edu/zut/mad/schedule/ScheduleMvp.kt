package pl.edu.zut.mad.schedule

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import org.joda.time.LocalDate


interface ScheduleMvp {

    interface View {
        fun onDateIntervalCalculated(minDate: LocalDate, maxDate: LocalDate)

        fun onLessonsEventLoad(lessonsEvents: MutableList<CalendarEvent>)

        fun showLoginView()

        fun showError()
    }

    interface Presenter {
        fun loadData()

        fun logout()

        fun refresh()
    }
}
