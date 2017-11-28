package pl.edu.zut.mad.schedule.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lesson_item.view.*
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal class LessonsAdapter : RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
        private val DATE_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT)
    }

    private var lessons: List<Lesson> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bindLessonWithDate(lessons[position])
    }

    override fun getItemCount() = lessons.size

    fun setLessons(lessons: List<Lesson>) {
        this.lessons = lessons
        notifyDataSetChanged()
    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindLessonWithDate(lesson: Lesson) {
            with(lesson) {
                itemView.dateView.text = DATE_FORMATTER.print(lesson.date)
                itemView.timeStartView.text = timeRange.start
                itemView.timeEndView.text = timeRange.end
                itemView.subjectWithTypeView.text = subjectWithType
                itemView.teacherWithRoomView.text = teacherWithRoom
            }
        }
    }
}
