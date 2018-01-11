package pl.edu.zut.mad.schedule

import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent

internal interface ScheduleMvp {

    interface View {
        fun onDateIntervalCalculated(minDate: LocalDate, maxDate: LocalDate)

        fun showLoadingView()

        fun hideLoadingView()

        fun setData(lessonsEvents: MutableList<LessonEvent>)

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
