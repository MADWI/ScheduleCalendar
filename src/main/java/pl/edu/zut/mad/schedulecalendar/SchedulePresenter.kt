package pl.edu.zut.mad.schedulecalendar

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.model.ui.LessonEvent
import pl.edu.zut.mad.schedulecalendar.util.NetworkConnection
import java.util.*
import kotlin.collections.ArrayList


class SchedulePresenter(private val repository: ScheduleRepository, private val user: User,
                        private val view: ScheduleMvp.View, private val networkConnection: NetworkConnection)
    : ScheduleMvp.Presenter {

    override fun logout() {
        user.delete()
        repository.delete()
        view.showLoginView()
    }

    override fun refresh() {
        if (networkConnection.isAvailable()) {
            view.showLoginView()
        } else {
            view.showError()
        }
    }

    override fun loadData() {
        if (user.isSaved()) {
            loadLessons()
        } else {
            view.showLoginView()
        }
    }

    private fun loadLessons() {
        val days = repository.getSchedule()
        val minDate = days.minBy { it.date }?.date?.withDayOfMonth(1) ?: LocalDate.now().minusMonths(3)
        val maxDate = days.maxBy { it.date }?.date?.withDayOfMonth(31) ?: LocalDate.now().plusMonths(3)

        val maxDateCal = dateToCalendar(minDate)
        val minDateCal = dateToCalendar(maxDate)
        view.onDateIntervalCalculated(maxDateCal, minDateCal)

        val events: MutableList<CalendarEvent> = ArrayList()
        var nextDay = minDate
        while (!nextDay.isEqual(maxDate)) {
            events.addAll(getLessonEventsForDayDate(nextDay))
            nextDay = nextDay.plusDays(1)
        }
        view.onLessonsEventLoad(events)
    }

    private fun dateToCalendar(date: LocalDate) = date.toDateTimeAtStartOfDay().toCalendar(Locale.getDefault())

    private fun getLessonEventsForDayDate(dayDate: LocalDate): MutableList<CalendarEvent> {
        val events: MutableList<CalendarEvent> = ArrayList()
        repository.getLessonsForDay(dayDate)?.lessons?.forEach {
            events.add(LessonEvent(dayDate, it))
        } ?: events.add(LessonEvent(dayDate, null))
        return events
    }
}
