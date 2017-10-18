package pl.edu.zut.mad.schedulecalendar.calendar

import android.view.View
import android.widget.TextView
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Lesson
import pl.edu.zut.mad.schedulecalendar.data.model.ui.LessonEvent


internal class CalendarAdapter : DefaultEventAdapter() {

    override fun getHeaderLayout() = R.layout.lesson_header

    override fun getEventLayout(isEmptyEvent: Boolean) = R.layout.lesson_item

    override fun getEventItemView(view: View, event: CalendarEvent, position: Int) {
        val lessonEvent = event as LessonEvent
        val lesson = lessonEvent.event as Lesson

        view.findViewById<TextView>(R.id.subjectWithTypeView).text = lesson.subjectWithType
        view.findViewById<TextView>(R.id.teacherWithRoomView).text = lesson.teacherWithRoom
    }
}
