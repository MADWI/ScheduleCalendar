package pl.edu.zut.mad.schedule.search

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SearchSchedulePagerAdapter(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = SearchByTeacherFragment()

    override fun getCount() = 3
}
