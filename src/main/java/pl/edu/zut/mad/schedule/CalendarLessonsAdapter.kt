package pl.edu.zut.mad.schedule

import android.graphics.Typeface
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import kotlinx.android.synthetic.main.day_header.view.*
import kotlinx.android.synthetic.main.lesson_calendar_item.view.*
import kotlinx.android.synthetic.main.lesson_teacher_and_subject.view.*
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import pl.edu.zut.mad.schedule.util.LessonFormatter
import java.util.Calendar

internal class CalendarLessonsAdapter(private val lessonClickListener: (Lesson) -> Unit)
    : DefaultEventAdapter() {

    companion object {
        private val STRIKE_THROUGH_SPAN = StrikethroughSpan()
    }

    override fun getHeaderLayout() = R.layout.day_header

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
                colorViewForeground(view, R.drawable.red_border)
                setStrikeThroughForTextView(view.subjectWithTypeView)
                setStrikeThroughForTextView(view.teacherWithRoomView)
            } else if (isExam) {
                colorViewForeground(view, R.drawable.blue_border)
                addExamPrefixToTextView(view.subjectWithTypeView)
            }
        }

    private fun setStrikeThroughForTextView(textView: TextView) {
        val text = textView.text
        textView.setText(text, TextView.BufferType.SPANNABLE)
        val spannable = textView.text as Spannable
        spannable.setSpan(STRIKE_THROUGH_SPAN, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun colorViewForeground(view: View, @DrawableRes resId: Int) {
        view.lessonCalendarItemLayout.foreground = ContextCompat.getDrawable(view.context, resId)
    }

    private fun addExamPrefixToTextView(textView: TextView) {
        val examText = "${textView.context.getText(R.string.exam)} "
        val spannableStringBuilder = SpannableStringBuilder(examText)
        spannableStringBuilder.setSpan(StyleSpan(Typeface.BOLD), 0,
            examText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.append(textView.text)
        textView.text = spannableStringBuilder
    }
}
