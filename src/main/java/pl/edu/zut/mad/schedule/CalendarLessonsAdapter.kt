package pl.edu.zut.mad.schedule

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import kotlinx.android.synthetic.main.lesson_calendar_item.view.*
import kotlinx.android.synthetic.main.lesson_teacher_and_subject.view.*
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal class CalendarLessonsAdapter(private val context: Context) : DefaultEventAdapter() {

    companion object {
        private const val GRAY_ITEM_POSITION_INTERVAL = 2
        private val DATE_FORMAT = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    }

    lateinit var lessonClickListener: (Lesson) -> Unit?

    private val itemViewColor by lazy {
        ContextCompat.getColor(context, R.color.scheduleLightGray)
    }
    private val timeViewColor by lazy {
        ContextCompat.getColor(context, R.color.scheduleColorPrimaryDark)
    }

    override fun getHeaderLayout() = R.layout.lesson_header

    override fun getHeaderItemView(view: View, day: Calendar) {
        view.findViewById<TextView>(R.id.lessonDayView).text = DATE_FORMAT.format(day.time)
    }

    override fun getEventLayout(isEmptyEvent: Boolean) =
        if (isEmptyEvent) R.layout.lesson_calendar_item else R.layout.no_lessons_item

    override fun getEventItemView(view: View, event: CalendarEvent, position: Int) {
        val lessonEvent = event as LessonEvent
        if (!lessonEvent.hasEvent()) {
            return
        }
        if (position.rem(GRAY_ITEM_POSITION_INTERVAL) == 0) {
            colorBackgroundToGray(view)
        }
        val lesson = lessonEvent.event as Lesson
        with(lesson) {
            view.timeStartView.text = timeRange.start
            view.timeEndView.text = timeRange.end
            view.subjectWithTypeView.text = subjectWithType
            view.teacherWithRoomView.text = teacherWithRoom
        }
        if (lesson.isCancelled) {
            view.cancelledTextView.visibility = View.VISIBLE
        }
        view.setOnClickListener {
            lessonClickListener.invoke(lessonEvent.event as Lesson)
        }
    }

    private fun colorBackgroundToGray(view: View) {
        view.timeGroupView.setBackgroundColor(timeViewColor)
        view.scheduleTaskItemView.setBackgroundColor(itemViewColor)
    }
}
