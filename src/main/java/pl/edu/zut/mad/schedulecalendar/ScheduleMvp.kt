package pl.edu.zut.mad.schedulecalendar

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import java.util.*


interface ScheduleMvp {

    interface View {
        fun onDateIntervalCalculated(minDate: Calendar, maxDate: Calendar)

        fun onLessonsEventLoad(lessonsEvents: MutableList<CalendarEvent>)
    }

    interface Presenter {
        fun loadLessons()

        fun deleteScheduleWithUser()
    }
}
