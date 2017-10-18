package pl.edu.zut.mad.schedulecalendar.data.model.ui

import com.ognev.kotlin.agendacalendarview.models.BaseCalendarEvent
import com.ognev.kotlin.agendacalendarview.models.IDayItem
import com.ognev.kotlin.agendacalendarview.models.IWeekItem
import java.util.*


class LessonEvent : BaseCalendarEvent {

    override lateinit var endTime: Calendar
    override lateinit var startTime: Calendar
    override lateinit var instanceDay: Calendar
    override lateinit var dayReference: IDayItem
    override lateinit var weekReference: IWeekItem
    override var event: Any? = null

    constructor()
}
