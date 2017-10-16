package pl.edu.zut.mad.schedulecalendar.lesson

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lessons.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.module.ScheduleCalendarModule
import pl.edu.zut.mad.schedulecalendar.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.app
import javax.inject.Inject


class LessonsFragment : Fragment() {

    companion object {
        private const val DATE_KEY = "schedule_calendar_date_key"

        fun newInstance(date: LocalDate): LessonsFragment {
            val arguments = Bundle()
            arguments.putSerializable(DATE_KEY, date)
            val fragment = LessonsFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    private lateinit var lessonsAdapter: LessonsAdapter
    private lateinit var date: LocalDate
    @Inject lateinit var scheduleRepository: ScheduleRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_lessons, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initInjections()
        readArguments()
        initListViewWithAdapter()
        initLessons()
    }

    private fun initInjections() {
        activity.app.component
                .plus(ScheduleCalendarModule())
                .inject(this)
    }

    private fun readArguments() {
        date = arguments.getSerializable(DATE_KEY) as LocalDate
    }

    private fun initListViewWithAdapter() {
        lessonsAdapter = LessonsAdapter(context)
        lessonsListView.adapter = lessonsAdapter
    }

    private fun initLessons() {
        val day = scheduleRepository.getDayByDate(date)
        val lessons = day?.lessons
        if (lessons == null || lessons.isEmpty()) { // TODO: clear
            noLessonsTextView.visibility = View.VISIBLE
            noLessonsImageView.visibility = View.VISIBLE
        } else {
            lessonsAdapter.lessons = day.lessons.toMutableList() // TODO: change conversion
            noLessonsTextView.visibility = View.GONE
            noLessonsImageView.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(DATE_KEY, date)
    }
}