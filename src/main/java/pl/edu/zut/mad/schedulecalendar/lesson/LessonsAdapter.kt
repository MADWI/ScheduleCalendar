package pl.edu.zut.mad.schedulecalendar.lesson

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lesson_item.view.*
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Lesson


internal class LessonsAdapter(private val context: Context)
    : RecyclerView.Adapter<LessonsAdapter.ClassViewHolder>() {

    var lessons: MutableList<Lesson> = ArrayList()
        set(value) {
            field.clear()
            lessons.addAll(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.lesson_item, parent, false)
        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bindLesson(lessons[position])
        if (position % 2 != 0) {
            colorItemToDark(holder)
        }
    }

    private fun colorItemToDark(holder: ClassViewHolder) {
        val itemViewColor = ContextCompat.getColor(context, R.color.scheduleLightGray)
        val timeViewColor = ContextCompat.getColor(context, R.color.scheduleColorPrimaryDark)
        with(holder.itemView) {
            scheduleTaskItemView.setBackgroundColor(itemViewColor)
            timeGroupView.setBackgroundColor(timeViewColor)
        }
    }

    override fun getItemCount() = lessons.size

    inner class ClassViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindLesson(lesson: Lesson) =
                with(itemView) {
                    timeStartView.text = lesson.timeRange.from
                    timeEndView.text = lesson.timeRange.to
                    subjectWithTypeView.text = lesson.subjectWithType
                    teacherWithRoomView.text = lesson.teacherWithRoom
                }
    }
}
