package pl.edu.zut.mad.schedulecalendar.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.schedule_task_item.view.*
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.Schedule


internal class ScheduleDayAdapter(private val context: Context)
    : RecyclerView.Adapter<ScheduleDayAdapter.ClassViewHolder>() {

    private val dayTasks: MutableList<Schedule.Hour> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.schedule_task_item, parent, false)
        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bindDayTasks(dayTasks[position])
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

    override fun getItemCount() = dayTasks.size

    fun setDayTasks(dayTasks: List<Schedule.Hour>?) {
        this.dayTasks.clear()
        if (dayTasks != null) {
            this.dayTasks.addAll(dayTasks)
        }
    }

    inner class ClassViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindDayTasks(hour: Schedule.Hour) =
                with(itemView) {
                    timeStartView.text = hour.startTime
                    timeEndView.text = hour.endTime
                    subjectView.text = hour.subjectNameWithType
                    lecturerAndRoomView.text = hour.lecturerWithRoom
                }
    }
}
