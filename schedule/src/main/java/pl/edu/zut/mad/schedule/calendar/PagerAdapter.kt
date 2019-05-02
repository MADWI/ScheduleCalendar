package pl.edu.zut.mad.schedule.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

internal abstract class PagerAdapter<in T>(
    private val items: List<T>,
    @LayoutRes private val layoutResource: Int
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(layoutResource))

    private fun ViewGroup.inflate(@LayoutRes resource: Int): View =
        LayoutInflater.from(context).inflate(resource, this, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBind(holder.itemView, items[position])
    }

    override fun getItemCount() = items.size

    abstract fun onBind(itemView: View, item: T)
}

internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
