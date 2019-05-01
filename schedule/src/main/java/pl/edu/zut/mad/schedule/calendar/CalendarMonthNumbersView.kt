package pl.edu.zut.mad.schedule.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.util.forEachChild

class CalendarMonthNumbersView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    init {
        View.inflate(context, R.layout.calendar_month, this)
        orientation = VERTICAL
    }

    fun setMonth(date: LocalDate) {
        val firstDayOfMonthWeek = date.withDayOfMonth(1)
        var date = firstDayOfMonthWeek.minusDays(firstDayOfMonthWeek.dayOfWeek - 1)
        forEachChild<ViewGroup> { weekView, _ ->
            weekView.forEachChild<TextView> { dayView, _ ->
                dayView.text = date.dayOfMonth.toString()
                date = date.plusDays(1)
            }
        }
    }
}
