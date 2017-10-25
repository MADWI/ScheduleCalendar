package pl.edu.zut.mad.schedule

import android.util.Log
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import io.reactivex.Observable
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.model.db.Day as DayDb
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
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
        val minDate = LocalDate.parse("2017-10-01")//days.minBy { it.date }?.date?.withDayOfMonth(1) ?: LocalDate.now().minusMonths(3) // TODO: get directly from repository
        val maxDate = minDate.plusMonths(5)//days.maxBy { it.date }?.date?.withDayOfMonth(31) ?: LocalDate.now().plusMonths(3) // TODO: get directly from repository

        val maxDateCal = dateToCalendar(minDate)
        val minDateCal = dateToCalendar(maxDate)
        view.onDateIntervalCalculated(maxDateCal, minDateCal)

        val events: MutableList<CalendarEvent> = ArrayList()
        val dateDates = getCalendarDates(minDate, maxDate)
        Observable.fromIterable(dateDates)
                .flatMapMaybe { repository.getLessonsForDay(it) }
                .subscribe(
                        { events.addAll(mapToLessonsEvents(it)) },
                        { Log.d(this.javaClass.simpleName, "error: ${it.message}") }, // TODO: print error to UI?
                        { view.onLessonsEventLoad(events) }
                )
    }

    private fun mapToLessonsEvents(day: DayUi): List<LessonEvent> =
            day.lessons.map { LessonEvent(day.date, it) }.toList()

    private fun dateToCalendar(date: LocalDate) =
            date.toDateTimeAtStartOfDay().toCalendar(Locale.getDefault())

    private fun getCalendarDates(minDate: LocalDate, maxDate: LocalDate): MutableList<LocalDate> {
        var nextDay = minDate
        val dateDates: MutableList<LocalDate> = ArrayList()
        while (!nextDay.isEqual(maxDate)) {
            dateDates.add(nextDay)
            nextDay = nextDay.plusDays(1)
        }
        return dateDates
    }
}
