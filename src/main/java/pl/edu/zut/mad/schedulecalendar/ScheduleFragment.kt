package pl.edu.zut.mad.schedulecalendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ognev.kotlin.agendacalendarview.builder.CalendarContentManager
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import kotlinx.android.synthetic.main.fragment_schedule.*
import pl.edu.zut.mad.schedulecalendar.login.LoginActivity
import pl.edu.zut.mad.schedulecalendar.module.ScheduleModule
import pl.edu.zut.mad.schedulecalendar.util.app
import java.util.*
import javax.inject.Inject


class ScheduleFragment : Fragment(), ScheduleMvp.View {

    companion object {
        private const val REQUEST_CODE = 123
    }

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
        presenter.loadData()
    }

    private fun initInjections() = app.component
            .plus(ScheduleModule(this))
            .inject(this)

    override fun showLoginView() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            presenter.loadData()
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
        presenter.logout()
    }

    fun refreshSchedule() {
        presenter.refresh()
    }

    override fun showError() {
        val contentView = activity.findViewById<View>(android.R.id.content)
        Snackbar.make(contentView, R.string.error_no_internet, Snackbar.LENGTH_SHORT).show()
    }
}
