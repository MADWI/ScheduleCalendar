package pl.edu.zut.mad.schedulecalendar.calendar

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Lesson
import pl.edu.zut.mad.schedulecalendar.data.model.ui.LessonEvent
import java.text.SimpleDateFormat
import java.util.*


internal class CalendarAdapter(private val context: Context) : DefaultEventAdapter() {

    companion object {
        private val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    }

    override fun getHeaderLayout() = R.layout.lesson_header

    override fun getHeaderItemView(view: View, day: Calendar) {
        view.findViewById<TextView>(R.id.lessonDayView).text = dateFormat.format(day.time)
    }

    override fun getEventLayout(isEmptyEvent: Boolean) = R.layout.lesson_item

    override fun getEventItemView(view: View, event: CalendarEvent, position: Int) {
        if (position % 2 == 0) {
            colorBackground(view)
        }

        val lessonEvent = event as LessonEvent
        val lesson = lessonEvent.event as Lesson
        view.findViewById<TextView>(R.id.timeStartView).text = lesson.timeRange.from
        view.findViewById<TextView>(R.id.timeEndView).text = lesson.timeRange.to
        view.findViewById<TextView>(R.id.subjectWithTypeView).text = lesson.subjectWithType
        view.findViewById<TextView>(R.id.teacherWithRoomView).text = lesson.teacherWithRoom
    }

    private fun colorBackground(view: View) {
        val itemViewColor = ContextCompat.getColor(context, R.color.scheduleLightGray)
        val timeViewColor = ContextCompat.getColor(context, R.color.scheduleColorPrimaryDark)
        val scheduleTaskItemView = view.findViewById<View>(R.id.scheduleTaskItemView)
        val timeGroupView = view.findViewById<View>(R.id.timeGroupView)
        scheduleTaskItemView.setBackgroundColor(itemViewColor)
        timeGroupView.setBackgroundColor(timeViewColor)
    }

}
