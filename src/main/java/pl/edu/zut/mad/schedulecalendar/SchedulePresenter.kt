package pl.edu.zut.mad.schedulecalendar

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.models.DayItem
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.model.ui.LessonEvent
import java.util.*
import kotlin.collections.ArrayList


class SchedulePresenter(private val repository: ScheduleRepository,
                        private val view: ScheduleMvp.View) : ScheduleMvp.Presenter {

    override fun loadLessons() {
        val minDate = Calendar.getInstance()
        minDate.add(Calendar.MONTH, -6) // TODO: get max and min date from schedule
        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.MONTH, 6)

        view.onDateIntervalCalculated(minDate, maxDate)

        val events: MutableList<CalendarEvent> = ArrayList()
        val minDay = LocalDate.fromCalendarFields(minDate)
        val firstDay = minDay.withDayOfMonth(1)
        var nextDay = LocalDate(firstDay)
        val lastDay = LocalDate.fromCalendarFields(maxDate).withDayOfMonth(1)
        while (!nextDay.isEqual(lastDay)) {
            nextDay = nextDay.plusDays(1)
            events.addAll(getLessonEventsForDayDate(nextDay))
        }
        view.onLessonsEventLoad(events)
    }

    private fun getLessonEventsForDayDate(dayDate: LocalDate): MutableList<CalendarEvent> {
        val events: MutableList<CalendarEvent> = ArrayList()
        val day = dayDate.toDateTimeAtStartOfDay().toCalendar(Locale.getDefault())
        val lessonDay = repository.getLessonsForDay(dayDate)
        if (lessonDay != null) {
            lessonDay.lessons.forEach {
                lessonDay.date
                val event = LessonEvent(day, day, DayItem.buildDayItemFromCal(day), it).setEventInstanceDay(day) // TODO: casting from "setEventInstanceDay" !!!
                events.add(event)
            }
        } else {
            val event = LessonEvent(day, day, DayItem.buildDayItemFromCal(day), null)
            events.add(event)
        }
        return events
    }
}
