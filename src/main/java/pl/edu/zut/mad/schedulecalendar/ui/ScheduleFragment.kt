package pl.edu.zut.mad.schedulecalendar.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.zut.mad.schedulecalendar.*
import pl.edu.zut.mad.schedulecalendar.model.Day
import pl.edu.zut.mad.schedulecalendar.module.ScheduleModule
import javax.inject.Inject


class ScheduleFragment : Fragment(), ScheduleMvp.View {

    companion object {
        private const val CALENDAR_TAG = "calendar_fragment"
        private const val SCHEDULE_TAG = "schedule_fragment"
    }

    private lateinit var calendarFragment: CalendarFragment
    private lateinit var lessonsPagerFragment: LessonsPagerFragment
    @Inject lateinit var schedulePresenter: SchedulePresenter
    @Inject lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.component.plus(ScheduleModule(this)).inject(this) // TODO: duplicated this with view injection
        schedulePresenter.fetchLessons()
        if (user.isSaved()) {
            initScheduleFragments(savedInstanceState)
        } else {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
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

    override fun showLoading() {
        log("showLoading")
    }

    override fun hideLoading() {
        log("hideLoading")
    }

    override fun setLessonsDays(lessonsDays: List<Day>) {
        log("setLessonsDays")
    }

    override fun showError() {
        log("showError")
    }

    private fun log(message: String) = Log.d("ScheduleFragment", message)
}
