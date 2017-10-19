package pl.edu.zut.mad.schedulecalendar.data.model.ui

import com.ognev.kotlin.agendacalendarview.models.*
import java.util.*


class LessonEvent : BaseCalendarEvent {

    override lateinit var endTime: Calendar
    override lateinit var startTime: Calendar
    override lateinit var instanceDay: Calendar
    override lateinit var dayReference: IDayItem
    override lateinit var weekReference: IWeekItem
    override var event: Any? = null

    constructor(startTime: Calendar,
                endTime: Calendar,
                dayItem: DayItem,
                lesson: Lesson?) {
        this.startTime = startTime
        this.endTime = endTime
        this.dayReference = dayItem
        this.event = lesson
    }

    override fun copy(): LessonEvent = LessonEvent(this)

    constructor(lessonEvent: LessonEvent)

    override fun hasEvent() = event != null
}
