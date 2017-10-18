package pl.edu.zut.mad.schedulecalendar.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ognev.kotlin.agendacalendarview.builder.CalendarContentManager
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.models.DayItem
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_schedule_agenda.*
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.calendar.CalendarAdapter
import pl.edu.zut.mad.schedulecalendar.calendar.CalendarController
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.model.ui.LessonEvent
import pl.edu.zut.mad.schedulecalendar.login.LoginActivity
import pl.edu.zut.mad.schedulecalendar.module.UserModule
import pl.edu.zut.mad.schedulecalendar.util.ModelMapper
import pl.edu.zut.mad.schedulecalendar.util.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.util.app
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class ScheduleFragment : Fragment() {

    companion object {
        private val NETWORK_UTILS = NetworkUtils()
        private const val REQUEST_CODE = 123
    }

    @Inject lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_schedule_agenda, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                initCalendar(savedInstanceState)
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

    // TODO: move to presenter, module
    private fun initCalendar(savedInstanceState: Bundle?) {
        val calendarManager = CalendarContentManager(CalendarController(), scheduleCalendarView, CalendarAdapter(activity))
        val minDate = Calendar.getInstance()
        minDate.add(Calendar.MONTH, -6)
        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.MONTH, 6)
        calendarManager.setDateRange(minDate, maxDate)

        val events: MutableList<CalendarEvent> = ArrayList()
        val repository = ScheduleRepository(Realm.getDefaultInstance(), ModelMapper())
        val days = repository.getSchedule()
        (days).forEach {
            val day = it.date.toDateTimeAtStartOfDay().toCalendar(Locale.getDefault())
            it.lessons.forEach {
                val event = LessonEvent(day, day, DayItem.buildDayItemFromCal(day), it).setEventInstanceDay(day)
                events.add(event)
            }
        }
        calendarManager.loadItemsFromStart(events)
    }

    private fun initAndStartScheduleFragments() {

    }

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
        // TODO: send action to refresh
        startActivity(intent)
    }
}
