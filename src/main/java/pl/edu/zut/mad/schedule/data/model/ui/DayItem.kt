package pl.edu.zut.mad.schedule.data.model.ui

import com.ognev.kotlin.agendacalendarview.models.IDayItem
import org.joda.time.LocalDate
import java.util.Date

internal class DayItem(date: LocalDate, override var isSelected: Boolean) : IDayItem {

    override var date: Date = date.toDate()
    override var eventsCount = 0
    override var isFirstDayOfTheMonth = false
    override var isToday = date.isEqual(LocalDate.now())
    override var month: String = date.monthOfYear().asShortText
    override var value = 0
    private var hasEvents: Boolean = false

    override fun hasEvents() = hasEvents

    override fun setHasEvents(hasEvents: Boolean) {
        this.hasEvents = hasEvents
    }

    override fun toString() = ""
}
