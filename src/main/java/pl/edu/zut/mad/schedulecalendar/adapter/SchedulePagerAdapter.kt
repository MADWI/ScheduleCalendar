package pl.edu.zut.mad.schedulecalendar.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import com.tobishiba.circularviewpager.library.BaseCircularViewPagerAdapter
import pl.edu.zut.mad.schedulecalendar.ScheduleDayFragment
import java.util.*
import kotlin.collections.ArrayList


internal class SchedulePagerAdapter(fragmentManager: FragmentManager)
    : BaseCircularViewPagerAdapter<Date>(fragmentManager, ArrayList<Date>()) {

    override fun getFragmentForItem(date: Date?): ScheduleDayFragment =
            ScheduleDayFragment.newInstance(date)

    override fun getItemPosition(obj: Any?) = PagerAdapter.POSITION_NONE
}
