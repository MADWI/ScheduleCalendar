package pl.edu.zut.mad.schedule.data.model.ui

import com.ognev.kotlin.agendacalendarview.models.IDayItem
import org.joda.time.LocalDate

internal class DayItem(date: LocalDate, override var isSelected: Boolean) : IDayItem {

    override var date = date.toDate()
    override var isFirstDayOfTheMonth = false
    override var isToday = date.isEqual(LocalDate.now())
    override var month = date.monthOfYear().asShortText
    override var value = 0
    override var eventsCount = 0
    private var hasEvents: Boolean = false

    override fun hasEvents() = hasEvents

    override fun setHasEvents(hasEvents: Boolean) {
        this.hasEvents = hasEvents
    }

    override fun toString() = ""
}
