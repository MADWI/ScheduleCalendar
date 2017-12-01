package pl.edu.zut.mad.schedule.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lesson_search_item.view.*
import kotlinx.android.synthetic.main.lesson_teacher_and_subject.view.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.ScheduleDate
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal class LessonsAdapter(private var lessons: List<Lesson>) :
    RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_search_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bindLesson(lessons[position])
    }

    override fun getItemCount() = lessons.size

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindLesson(lesson: Lesson) {
            with(lesson) {
                itemView.dateView.text = ScheduleDate.UI_FORMATTER.print(lesson.date)
                itemView.timeView.text = String.format(ScheduleDate.TIME_SEARCH_LESSON_PATTERN, timeRange.start, timeRange.end)
                itemView.subjectWithTypeView.text = subjectWithType
                itemView.teacherWithRoomView.text = teacherWithRoom
            }
        }
    }
}
