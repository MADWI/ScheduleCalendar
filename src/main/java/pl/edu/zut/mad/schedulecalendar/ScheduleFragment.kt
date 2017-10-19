package pl.edu.zut.mad.schedulecalendar

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
import kotlinx.android.synthetic.main.fragment_schedule.*
import pl.edu.zut.mad.schedulecalendar.login.LoginActivity
import pl.edu.zut.mad.schedulecalendar.module.ScheduleModule
import pl.edu.zut.mad.schedulecalendar.util.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.util.app
import java.util.*
import javax.inject.Inject


class ScheduleFragment : Fragment(), ScheduleMvp.View {

    companion object {
        private val NETWORK_UTILS = NetworkUtils()
        private const val REQUEST_CODE = 123
    }

    @Inject lateinit var user: User
    @Inject lateinit var presenter: SchedulePresenter
    private val calendarContentManager: CalendarContentManager by lazy {
        CalendarContentManager(CalendarController(), scheduleCalendarView, LessonsAdapter(activity))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initInjections()
        initView()
    }

    private fun initInjections() = app.component
            .plus(ScheduleModule(this))
            .inject(this)

    private fun initView() =
            if (user.isSaved()) {
                presenter.loadLessons()
            } else {
                startLoginActivity()
            }

    private fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            presenter.loadLessons()
        }
    }

    override fun onDateIntervalCalculated(minDate: Calendar, maxDate: Calendar) {
        calendarContentManager.locale = Locale.getDefault()
        calendarContentManager.setDateRange(minDate, maxDate)
    }

    override fun onLessonsEventLoad(lessonsEvents: MutableList<CalendarEvent>) {
        calendarContentManager.loadItemsFromStart(lessonsEvents)
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
