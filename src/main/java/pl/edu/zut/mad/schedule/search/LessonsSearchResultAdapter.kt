package pl.edu.zut.mad.schedule.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lesson_search_item.view.*
import kotlinx.android.synthetic.main.lesson_teacher_and_subject.view.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.ScheduleDate
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.LessonItemPainter

internal class LessonsSearchResultAdapter(private var lessons: List<Lesson>, context: Context) :
    RecyclerView.Adapter<LessonsSearchResultAdapter.LessonViewHolder>() {

    private val lessonPainter = LessonItemPainter(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_search_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        lessonPainter.colorBackgroundToGrayIfShould(holder.itemView, position)
        holder.bindLesson(lessons[position])
    }

    override fun getItemCount() = lessons.size

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindLesson(lesson: Lesson) {
            with(lesson) {
                itemView.dateView.text = ScheduleDate.UI_FORMATTER.print(lesson.date)
                itemView.timeView.text = String.format(ScheduleDate.TIME_SEARCH_LESSON_PATTERN, timeRange.start, timeRange.end)
                itemView.subjectWithTypeView.text = "$subject ($type)" //TODO LessonFormatter
                itemView.teacherWithRoomView.text = teacherWithRoom
            }
        }
    }
}
