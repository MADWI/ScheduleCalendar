package pl.edu.zut.mad.schedule.data.model.ui

import com.ognev.kotlin.agendacalendarview.models.*
import org.joda.time.LocalDate
import java.util.*


class LessonEvent : BaseCalendarEvent {

    constructor(date: LocalDate, lesson: Lesson?) {
        val day = date.toDateTimeAtStartOfDay().toCalendar(Locale.getDefault())
        this.startTime = day
        this.endTime = day
        this.dayReference = DayItem.buildDayItemFromCal(day)
        this.event = lesson
        setEventInstanceDay(day)
    }

    override fun copy(): LessonEvent = LessonEvent(this)

    constructor(lessonEvent: LessonEvent)

    override fun hasEvent() = event != null
}
