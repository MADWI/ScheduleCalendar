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

internal class CalendarMonthNumbersView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private var selectedDayView: TextView? = null
        set(value) {
            field?.setBackgroundResource(0)
            value?.setBackgroundResource(R.drawable.calendar_day_selected_background)
            field = value
        }

    init {
        View.inflate(context, R.layout.calendar_month_numbers, this)
        orientation = VERTICAL
    }

    fun setup(lessonDays: List<LocalDate>, dateClickListener: (LocalDate) -> Unit) {
        val monthFirstDate = lessonDays[0].withDayOfMonth(1)
        var date = monthFirstDate.withDayOfWeek(DateTimeConstants.MONDAY)
        forEachDayView {
            setupDayView(it, date, lessonDays, dateClickListener)
            date = date.plusDays(1)
        }
    }

    fun setSelectedDate(date: LocalDate) {
        selectedDayView = findDayViewByDate(date)
    }

    private fun setupDayView(
        dayView: TextView,
        date: LocalDate,
        lessonDays: List<LocalDate>,
        dateClickListener: (LocalDate) -> Unit
    ) {
        dayView.tag = date
        dayView.text = date.dayOfMonth.toString()
        dayView.setOnClickListener {
            selectedDayView = dayView
            dateClickListener(date)
        }
        if (date in lessonDays) {
            dayView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.calendar_day_lessons_indicator)
        }
    }

    private fun findDayViewByDate(date: LocalDate): TextView? {
        var dayView: TextView? = null
        forEachDayView {
            if (it.tag as LocalDate == date) {
                dayView = it
            }
        }
        return dayView
    }

    private fun forEachDayView(onDay: (TextView) -> Unit) {
        forEachChild<ViewGroup> { weekView, _ ->
            weekView.forEachChild<TextView> { dayView, _ ->
                onDay(dayView)
            }
        }
    }
}
