package pl.edu.zut.mad.schedulecalendar.calendar

import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import pl.edu.zut.mad.schedulecalendar.R


internal class CalendarAdapter : DefaultEventAdapter() {

    override fun getHeaderLayout() = R.layout.lesson_header

    override fun getEventLayout(isEmptyEvent: Boolean) = R.layout.lesson_item
}
