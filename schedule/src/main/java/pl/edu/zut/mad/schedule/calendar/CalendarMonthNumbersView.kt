package pl.edu.zut.mad.schedule.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.util.forEachChild

internal class CalendarMonthNumbersView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    lateinit var onDateClick: (LocalDate) -> Unit

    init {
        View.inflate(context, R.layout.calendar_month_numbers, this)
        orientation = VERTICAL
    }

    fun setMonth(lessonDays: List<LocalDate>) {
        var date = lessonDays[0].withDayOfMonth(1).withDayOfWeek(DateTimeConstants.MONDAY)
        forEachChild<ViewGroup> { weekView, _ ->
            weekView.forEachChild<TextView> { dayView, _ ->
                setupDayView(dayView, date, lessonDays)
                date = date.plusDays(1)
            }
        }
    }

    private fun setupDayView(dayView: TextView, date: LocalDate, lessonDays: List<LocalDate>) {
        dayView.text = date.dayOfMonth.toString()
        dayView.tag = date
        dayView.setOnClickListener { onDateClick(it.tag as LocalDate) }
        if (date in lessonDays) {
            dayView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.calendar_day_lessons_indicator)
        }
    }
}
