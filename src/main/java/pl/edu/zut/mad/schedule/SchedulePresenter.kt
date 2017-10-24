package pl.edu.zut.mad.schedule

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.model.db.Day
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import pl.edu.zut.mad.schedule.util.NetworkConnection
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
        val days = ArrayList<Day>()//repository.getSchedule()
        val minDate = LocalDate.parse("2017-10-01")//days.minBy { it.date }?.date?.withDayOfMonth(1) ?: LocalDate.now().minusMonths(3) // TODO: get directly from repository
        val maxDate = minDate.plusMonths(5)//days.maxBy { it.date }?.date?.withDayOfMonth(31) ?: LocalDate.now().plusMonths(3) // TODO: get directly from repository

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
