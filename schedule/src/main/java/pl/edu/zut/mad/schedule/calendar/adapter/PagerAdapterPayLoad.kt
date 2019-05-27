package pl.edu.zut.mad.schedule.calendar.adapter

import android.view.View
import androidx.annotation.LayoutRes
import pl.edu.zut.mad.schedule.calendar.PagerAdapter
import pl.edu.zut.mad.schedule.calendar.ViewHolder

internal abstract class PagerAdapterPayLoad<in T, in U>(
    items: List<T>,
    @LayoutRes layoutResource: Int
) : PagerAdapter<T>(items, layoutResource) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            @Suppress("UNCHECKED_CAST")
            val payload = payloads[0] as U
            onBindWithPayload(holder.itemView, payload)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    abstract fun onBindWithPayload(itemView: View, payload: U)
}
