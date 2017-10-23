package pl.edu.zut.mad.schedule

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import java.util.*


interface ScheduleMvp {

    interface View {
        fun onDateIntervalCalculated(minDate: Calendar, maxDate: Calendar)

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
