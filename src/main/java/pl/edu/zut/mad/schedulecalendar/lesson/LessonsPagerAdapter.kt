package pl.edu.zut.mad.schedulecalendar.lesson

import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import com.tobishiba.circularviewpager.library.BaseCircularViewPagerAdapter
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.lesson.LessonsFragment


internal class LessonsPagerAdapter(fragmentManager: FragmentManager)
    : BaseCircularViewPagerAdapter<LocalDate>(fragmentManager, ArrayList<LocalDate>()) {

    override fun getFragmentForItem(date: LocalDate): LessonsFragment =
            LessonsFragment.newInstance(date)

    override fun getItemPosition(obj: Any?) = PagerAdapter.POSITION_NONE
}
