package pl.edu.zut.mad.schedule.calendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.joda.time.LocalDate

class CalendarFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItem(position: Int): Fragment {
        val date = LocalDate.now().plusMonths(position)
        return CalendarMonthFragment.newInstance(date)
    }

    override fun getItemCount() = 5
}
