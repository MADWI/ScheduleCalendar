package pl.edu.zut.mad.schedule

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import org.joda.time.LocalDate

internal interface ScheduleMvp {

    interface View {
        fun onDateIntervalCalculated(minDate: LocalDate, maxDate: LocalDate)

        fun showLoadingView()

        fun hideLoadingView()

        fun setData(lessonsEvents: MutableList<CalendarEvent>)

        fun showLoginView()

        fun showInternetError()

        fun refreshSchedule(albumNumber: Int)
    }

    interface Presenter {
        fun onViewIsCreated()

        fun logout()

        fun refresh()
    }
}
