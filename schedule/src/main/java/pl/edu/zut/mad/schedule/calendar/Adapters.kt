package pl.edu.zut.mad.schedule.calendar

import android.view.View
import kotlinx.android.synthetic.main.calendar_month.view.*
import kotlinx.android.synthetic.main.lessons_day.view.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Day

internal class CalendarPagerAdapter(monthDays: List<List<Day>>) : PagerAdapter<List<Day>>(monthDays, R.layout.calendar_month) {

    override fun onBind(itemView: View, item: List<Day>) {
        val date = item[0].date
        itemView.calendarMonthNumbersView.setMonth(date)
        itemView.monthNameView.text = date.toString(Format.MONTH_NAME)
    }
}

internal class LessonsPagerAdapter(lessons: List<Day>) : PagerAdapter<Day>(lessons, R.layout.lessons_day) {

    override fun onBind(itemView: View, item: Day) {
        itemView.lessonsDayListView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(itemCount)
            adapter = LessonsAdapter(item.lessons)
        }
    }
}
