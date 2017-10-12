package pl.edu.zut.mad.schedulecalendar.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.zut.mad.schedulecalendar.R


class ScheduleFragment : Fragment() {

    companion object {
        private const val CALENDAR_TAG = "calendar_fragment"
        private const val SCHEDULE_TAG = "schedule_fragment"
    }

    private lateinit var calendarFragment: CalendarFragment
    private lateinit var lessonsPagerFragment: LessonsPagerFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScheduleFragments(savedInstanceState)
    }

    private fun initScheduleFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            initAndStartScheduleFragments()
        } else {
            initScheduleFragmentsFromStack()
        }
        lessonsPagerFragment.registerCalendar(calendarFragment)
    }

    private fun initAndStartScheduleFragments() {
        calendarFragment = CalendarFragment()
        lessonsPagerFragment = LessonsPagerFragment()
        replaceFragmentInViewContainer(calendarFragment, R.id.calendar_container, CALENDAR_TAG)
        replaceFragmentInViewContainer(lessonsPagerFragment, R.id.schedule_container, SCHEDULE_TAG)
    }

    private fun replaceFragmentInViewContainer(fragment: Fragment, containerId: Int, tag: String) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .commit()
    }

    private fun initScheduleFragmentsFromStack() {
        calendarFragment = getFragmentFromStackWithTag(CALENDAR_TAG) as CalendarFragment
        lessonsPagerFragment = getFragmentFromStackWithTag(SCHEDULE_TAG) as LessonsPagerFragment
    }

    private fun getFragmentFromStackWithTag(tag: String): Fragment =
            fragmentManager.findFragmentByTag(tag)
}
