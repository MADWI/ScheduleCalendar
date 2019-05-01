package pl.edu.zut.mad.schedule.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.util.forEachChild

class CalendarWeekNamesView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    init {
        View.inflate(context, R.layout.calendar_week_names, this)
        orientation = HORIZONTAL
        setupNames()
    }

    private fun setupNames() {
        val formatter = DateTimeFormat.forPattern(Format.DAY_NAME)
        val date = LocalDate()
        forEachChild<TextView> { dayView, index ->
            dayView.text = date.withDayOfWeek(index + 1).toString(formatter)
        }
    }
}