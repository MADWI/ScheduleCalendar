package pl.edu.zut.mad.schedulecalendar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ScheduleFragment : Fragment() {

    companion object {
        private const val CALENDAR_TAG = "calendar_fragment"
        private const val SCHEDULE_TAG = "schedule_fragment"
    }

    private lateinit var calendarFragment: CalendarFragment
    private lateinit var schedulePagerFragment: SchedulePagerFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScheduleFragments(savedInstanceState)
    }

    private fun initScheduleFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            calendarFragment = CalendarFragment()
            schedulePagerFragment = SchedulePagerFragment()
            startScheduleFragments()
        } else {
            initScheduleFragmentsFromStack()
        }
        registerCalendarForScheduleFragment()
    }

    private fun initScheduleFragmentsFromStack() {
        calendarFragment = getFragmentFromStackWithTag(CALENDAR_TAG) as CalendarFragment
        schedulePagerFragment = getFragmentFromStackWithTag(SCHEDULE_TAG) as SchedulePagerFragment
    }

    private fun getFragmentFromStackWithTag(tag: String): Fragment =
            fragmentManager.findFragmentByTag(tag)

    private fun startScheduleFragments() {
        replaceFragmentInViewContainer(calendarFragment, R.id.calendar_container, CALENDAR_TAG)
        replaceFragmentInViewContainer(schedulePagerFragment, R.id.schedule_container, SCHEDULE_TAG)
    }

    private fun replaceFragmentInViewContainer(fragment: Fragment, containerId: Int, tag: String) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .commit()
    }

    private fun registerCalendarForScheduleFragment() {
        schedulePagerFragment.registerCalendar(calendarFragment)
    }
}
