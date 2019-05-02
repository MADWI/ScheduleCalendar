package pl.edu.zut.mad.schedule.calendar

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.lesson_calendar_item.view.*
import kotlinx.android.synthetic.main.lesson_teacher_and_subject.view.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.LessonFormatter

internal class LessonsAdapter(lessons: List<Lesson>) : PagerAdapter<Lesson>(lessons, R.layout.lesson_calendar_item) {

    override fun onBind(itemView: View, item: Lesson) {
        val lessonFormatter = LessonFormatter(item)
        itemView.timeStartView.text = item.timeRange.start
        itemView.timeEndView.text = item.timeRange.end
        itemView.subjectWithTypeView.text = lessonFormatter.getSubjectWithType()
        itemView.teacherWithRoomView.text = lessonFormatter.getTeacherWithRoom()
        if (item.isCancelled) {
            itemView.lessonCalendarItemLayout.setForegroundColor(R.color.red_transparent)
            setStrikeThroughForTextView(itemView.subjectWithTypeView)
            setStrikeThroughForTextView(itemView.teacherWithRoomView)
        } else if (item.isExam) {
            itemView.lessonCalendarItemLayout.setForegroundColor(R.color.blue_transparent)
            addExamPrefixToTextView(itemView.subjectWithTypeView)
        }
    }

    private fun FrameLayout.setForegroundColor(@ColorRes id: Int) {
        foreground = ColorDrawable(ContextCompat.getColor(context, id))
    }

    private fun setStrikeThroughForTextView(textView: TextView) {
        val text = textView.text
        textView.setText(text, TextView.BufferType.SPANNABLE)
        val spannable = textView.text as Spannable
        spannable.setSpan(StrikethroughSpan(), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun addExamPrefixToTextView(textView: TextView) {
        val examText = "${textView.context.getText(R.string.exam)} "
        val spannableStringBuilder = SpannableStringBuilder(examText)
        val styleSpan = StyleSpan(Typeface.BOLD)
        spannableStringBuilder.setSpan(styleSpan, 0, examText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.append(textView.text)
        textView.text = spannableStringBuilder
    }
}
