package pl.edu.zut.mad.schedule.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lesson_search_item.view.*
import kotlinx.android.synthetic.main.lesson_teacher_and_subject.view.*
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal class LessonsAdapter(private var lessons: List<Lesson>) :
    RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy" // TODO  move to config class
        private val DATE_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT)
    }

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
                itemView.dateView.text = DATE_FORMATTER.print(lesson.date)
                itemView.timeView.text = String.format("%s-%s", timeRange.start, timeRange.end) // TODO move to config class
                itemView.subjectWithTypeView.text = subjectWithType
                itemView.teacherWithRoomView.text = teacherWithRoom
            }
        }
    }
}
