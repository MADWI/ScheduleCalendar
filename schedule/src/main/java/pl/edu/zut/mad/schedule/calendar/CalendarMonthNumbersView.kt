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

    init {
        View.inflate(context, R.layout.calendar_month_numbers, this)
        orientation = VERTICAL
    }

    fun setup(monthFirstDate: LocalDate, onDay: (TextView, LocalDate) -> Unit) {
        var date = monthFirstDate.withDayOfWeek(DateTimeConstants.MONDAY)
        forEachChild<ViewGroup> { weekView, _ ->
            weekView.forEachChild<TextView> { dayView, _ ->
                onDay(dayView, date)
                date = date.plusDays(1)
            }
        }
    }
}
