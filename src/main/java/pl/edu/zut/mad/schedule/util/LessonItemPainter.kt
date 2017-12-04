package pl.edu.zut.mad.schedule.util

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.lesson_calendar_item.view.*
import pl.edu.zut.mad.schedule.R

internal class LessonItemPainter(private val context: Context) {

    companion object {
        private const val GRAY_ITEM_POSITION_INTERVAL = 2
    }

    private val itemViewColorGray by lazy {
        ContextCompat.getColor(context, R.color.scheduleLightGray)
    }

    private val timeViewColorGray by lazy {
        ContextCompat.getColor(context, R.color.scheduleColorPrimaryDark)
    }

    private val itemViewColor by lazy {
        ContextCompat.getColor(context, R.color.white)
    }

    private val timeViewColor by lazy {
        ContextCompat.getColor(context, R.color.scheduleColorPrimary)
    }

    fun colorBackgroundToGrayIfShould(view: View, position: Int) {
        if (position.rem(GRAY_ITEM_POSITION_INTERVAL) == 0) {
            view.timeGroupView.setBackgroundColor(timeViewColorGray)
            view.setBackgroundColor(itemViewColorGray)
        } else {
            view.timeGroupView.setBackgroundColor(timeViewColor)
            view.setBackgroundColor(itemViewColor)
        }
    }
}
