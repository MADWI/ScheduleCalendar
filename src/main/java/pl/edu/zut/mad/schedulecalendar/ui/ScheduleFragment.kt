package pl.edu.zut.mad.schedulecalendar.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.realm.Realm
import pl.edu.zut.mad.schedulecalendar.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.app
import pl.edu.zut.mad.schedulecalendar.lesson.LessonsPagerFragment
import pl.edu.zut.mad.schedulecalendar.login.LoginActivity
import pl.edu.zut.mad.schedulecalendar.module.UserModule
import javax.inject.Inject


class ScheduleFragment : Fragment() {

    companion object {
        private val NETWORK_UTILS = NetworkUtils()
        private const val CALENDAR_TAG = "calendar_fragment"
        private const val SCHEDULE_TAG = "schedule_fragment"
        private const val REQUEST_CODE = 123
    }

    private lateinit var calendarFragment: CalendarFragment
    private lateinit var lessonsPagerFragment: LessonsPagerFragment
    @Inject lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Realm.init(activity)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        initInjections()
        initView(savedInstanceState)
    }

    private fun initInjections() = app.component
            .plus(UserModule())
            .inject(this)

    private fun initView(savedInstanceState: Bundle?) =
        if (user.isSaved()) {
            initScheduleFragments(savedInstanceState)
        } else {
            startLoginActivity()
        }

    private fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            initAndStartScheduleFragments()
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

    fun logout() {
        user.remove()
        startLoginActivity()
    }

    fun refreshSchedule() {
        if (NETWORK_UTILS.isAvailable(activity)) {
            startLoginActivityWithSavedAlbumNumber()
        } else {
            Toast.makeText(activity, R.string.error_no_internet, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startLoginActivityWithSavedAlbumNumber() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.putExtra(User.ALBUM_NUMBER_KEY, user.getAlbumNumber())
        startActivity(intent)
    }

}
