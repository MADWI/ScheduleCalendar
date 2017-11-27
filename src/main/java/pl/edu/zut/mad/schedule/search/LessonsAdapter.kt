package pl.edu.zut.mad.schedule.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lesson_item.view.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Day
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal class LessonsAdapter : RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    private var lessons: List<Lesson> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bindLessonWithDate(lessons[position])
    }

    override fun getItemCount() = lessons.size

    fun setDays(days: List<Day>) {
        lessons = days.flatMap { it.lessons }
        notifyDataSetChanged()
    }

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindLessonWithDate(lesson: Lesson) {
            with(lesson) {
                itemView.dateView.text = "27-11-2017" // TODO: add date to ui.Lesson model
                itemView.timeStartView.text = timeRange.from
                itemView.timeEndView.text = timeRange.to
                itemView.subjectWithTypeView.text = subjectWithType
                itemView.teacherWithRoomView.text = teacherWithRoom
            }
        }
    }
}
