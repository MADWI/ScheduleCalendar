package pl.edu.zut.mad.schedule

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
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.animation.AnimationParams
import pl.edu.zut.mad.schedule.animation.CircularRevealAnimation
import pl.edu.zut.mad.schedule.login.LoginActivity
import pl.edu.zut.mad.schedule.module.ScheduleComponent
import pl.edu.zut.mad.schedule.module.ScheduleModule
import pl.edu.zut.mad.schedule.search.SearchActivity
import pl.edu.zut.mad.schedule.util.Animations
import pl.edu.zut.mad.schedule.util.app
import pl.edu.zut.mad.schedule.util.log
import java.util.Calendar
import javax.inject.Inject

open class ScheduleFragment : Fragment(), ComponentView<ScheduleComponent>, ScheduleMvp.View {

    companion object {
        internal const val REQUEST_CODE = 123
    }

    var dateListener: DateListener? = null
    @Inject internal lateinit var presenter: ScheduleMvp.Presenter

    private val calendarContentManager: CalendarContentManager by lazy {
        val calendarController = CalendarController()
        calendarController.dateListener = dateListener
        val lessonAdapter = getLessonAdapter()
        CalendarContentManager(calendarController, scheduleCalendarView, lessonAdapter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun getComponent() = app.component.plus(ScheduleModule(this))

    private fun init() {
        initInjections()
        presenter.onViewIsCreated()
    }

    private fun initInjections() = getComponent().inject(this)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            presenter.onViewIsCreated()
            view!!.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    v!!.removeOnLayoutChangeListener(this)
                    val animationSettings = data?.getSerializableExtra("animation_settings_key") as AnimationParams
                    Animations.registerAnimation(view!!, CircularRevealAnimation(view!!, animationSettings, R.color.scheduleColorPrimaryDark, R.color.white))
                    log("onLayoutChange")
                }
            })
        } else if (resultCode == Activity.RESULT_CANCELED) {
            activity.finish()
        }
    }

    override fun onDateIntervalCalculated(minDate: LocalDate, maxDate: LocalDate) =
        calendarContentManager.setDateRange(dateToCalendar(minDate), dateToCalendar(maxDate))

    private fun dateToCalendar(date: LocalDate): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date.toDate()
        return calendar
    }

    override fun onLessonsEventsLoad(lessonsEvents: MutableList<CalendarEvent>) =
        calendarContentManager.loadItemsFromStart(lessonsEvents)

    override fun showLoginView() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun refreshSchedule(albumNumber: Int) {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.putExtra(LoginActivity.ALBUM_NUMBER_KEY, albumNumber)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun showInternetError() {
        val contentView = activity.findViewById<View>(android.R.id.content)
        Snackbar.make(contentView, R.string.error_no_internet, Snackbar.LENGTH_SHORT).show()
    }

    fun refreshSchedule() = presenter.refresh()

    fun logout() = presenter.logout()

    fun moveToToday() =
        calendarContentManager.agendaCalendarView
            .agendaView
            .agendaListView
            .scrollToCurrentDate(Calendar.getInstance())

    private fun getLessonAdapter(): CalendarLessonsAdapter {
        val lessonAdapter = CalendarLessonsAdapter(activity)
        lessonAdapter.lessonClickListener = {
            val intent = SearchActivity.getIntentWithLesson(context, it)
            startActivity(intent)
        }
        return lessonAdapter
    }
}
