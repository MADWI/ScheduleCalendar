package pl.edu.zut.mad.schedule

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.TextView
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import kotlinx.android.synthetic.main.lesson_calendar_item.view.*
import kotlinx.android.synthetic.main.lesson_header.view.*
import kotlinx.android.synthetic.main.lesson_teacher_and_subject.view.*
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import pl.edu.zut.mad.schedule.util.LessonFormatter
import pl.edu.zut.mad.schedule.util.LessonItemPainter
import java.util.Calendar

internal class CalendarLessonsAdapter(context: Context) : DefaultEventAdapter() {

    companion object {
        private val STRIKE_THROUGH_SPAN = StrikethroughSpan()
    }

    private val lessonItemPainter = LessonItemPainter(context)

    lateinit var lessonClickListener: (Lesson) -> Unit?

    override fun getHeaderLayout() = R.layout.lesson_header

    override fun getHeaderItemView(view: View, day: Calendar) {
        val headerText = ScheduleDate.UI_LESSON_HEADER_FORMATTER.print(day.time.time)
        view.lessonHeaderDateView.text = headerText
    }

    override fun getEventLayout(isEmptyEvent: Boolean) =
        if (isEmptyEvent) R.layout.lesson_calendar_item else R.layout.no_lessons_item

    override fun getEventItemView(view: View, event: CalendarEvent, position: Int) {
        val lessonEvent = event as LessonEvent
        if (!lessonEvent.hasEvent()) {
            return
        }
        val lesson = lessonEvent.event as Lesson
        bindLesson(lesson, view)
        lessonItemPainter.colorBackgroundToGrayIfShould(view, position)
        view.setOnClickListener { lessonClickListener.invoke(lessonEvent.event as Lesson) }
    }

    private fun bindLesson(lesson: Lesson, view: View) =
        with(lesson) {
            val lessonFormatter = LessonFormatter(this)
            view.timeStartView.text = timeRange.start
            view.timeEndView.text = timeRange.end
            view.subjectWithTypeView.text = lessonFormatter.getSubjectWithType()
            view.teacherWithRoomView.text = lessonFormatter.getTeacherWithRoom()
            if (isCancelled) {
                view.lessonCalendarItemLayout.foreground =
                    ContextCompat.getDrawable(view.context, R.drawable.red_border)
                modifyViewsToCancelled(view)
            } else if (isExam) {
                view.examStatusTextView.visibility = View.VISIBLE
            }
        }

    private fun modifyViewsToCancelled(view: View) {
        modifyToCancelled(view.subjectWithTypeView)
        modifyToCancelled(view.teacherWithRoomView)
    }

    private fun modifyToCancelled(textView: TextView) {
        val text = textView.text
        textView.setText(text, TextView.BufferType.SPANNABLE)
        val spannable = textView.text as Spannable
        spannable.setSpan(STRIKE_THROUGH_SPAN, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}
