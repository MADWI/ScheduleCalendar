package pl.edu.zut.mad.schedule.calendar

import android.view.View
import kotlinx.android.synthetic.main.calendar_month.view.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R

internal class CalendarPagerAdapter(monthDays: List<List<LocalDate>>, private val onDateClick: (LocalDate) -> Unit) :
    PagerAdapter<List<LocalDate>>(monthDays, R.layout.calendar_month) {

    override fun onBind(itemView: View, dates: List<LocalDate>) {
        val monthFirstDay = dates[0].withDayOfMonth(1)
        itemView.monthNameView.text = monthFirstDay.toString(Format.MONTH_NAME)
        itemView.calendarMonthNumbersView.setup(dates, onDateClick)
    }
}
