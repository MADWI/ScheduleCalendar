package pl.edu.zut.mad.schedule

import com.ognev.kotlin.agendacalendarview.CalendarController
import com.ognev.kotlin.agendacalendarview.models.IDayItem
import org.joda.time.LocalDate
import java.util.*


internal class CalendarController(private val dateListener: DateListener?) : CalendarController {

    private var previousDate: LocalDate? = null

    override fun onDaySelected(dayItem: IDayItem) {
        val selectedDate = LocalDate.fromDateFields(dayItem.date)
        if (selectedDate != previousDate) {
            previousDate = selectedDate
            dateListener?.onDateChanged(selectedDate)
        }
    }

    override fun onScrollToDate(calendar: Calendar) {
        val selectedDate = LocalDate.fromCalendarFields(calendar)
        if (selectedDate != previousDate) {
            previousDate = selectedDate
            dateListener?.onDateChanged(selectedDate)
        }
    }

    override fun getEmptyEventLayout() = 0

    override fun getEventLayout() = 0
}
