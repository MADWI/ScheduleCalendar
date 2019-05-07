package pl.edu.zut.mad.schedule.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lessons_day.view.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Day

internal class LessonsPagerAdapter(private val days: List<Day>) :
    RecyclerView.Adapter<LessonsPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val resource = if (ViewType.LIST == viewType) R.layout.lessons_day else R.layout.no_lessons_item
        val view = LayoutInflater.from(parent.context).inflate(resource, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        if (day.lessons.isEmpty()) return
        holder.itemView.lessonsDayListView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(itemCount)
            adapter = LessonsDayAdapter(day.lessons)
        }
    }

    override fun getItemCount() = days.size

    override fun getItemViewType(position: Int) =
        if (days[position].lessons.isNotEmpty()) ViewType.LIST else ViewType.EMPTY

    private object ViewType {
        const val LIST = 0
        const val EMPTY = 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
