package pl.edu.zut.mad.schedule.calendar

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.calendar_month.view.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R

internal class CalendarPagerAdapter(monthDays: List<List<LocalDate>>, private val onDateClick: (LocalDate) -> Unit) :
    PagerAdapter<List<LocalDate>>(monthDays, R.layout.calendar_month) {

    override fun onBind(itemView: View, dates: List<LocalDate>) {
        val monthFirstDay = dates[0].withDayOfMonth(1)
        itemView.monthNameView.text = monthFirstDay.toString(Format.MONTH_NAME)
        itemView.calendarMonthNumbersView.setup(monthFirstDay) { dayView, date ->
            setupDayView(dayView, date, dates)
        }
    }

    private fun setupDayView(dayView: TextView, date: LocalDate, lessonDays: List<LocalDate>) {
        dayView.text = date.dayOfMonth.toString()
        dayView.setOnClickListener { onDateClick(date) }
        if (date in lessonDays) {
            dayView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.calendar_day_lessons_indicator)
        }
    }
}
