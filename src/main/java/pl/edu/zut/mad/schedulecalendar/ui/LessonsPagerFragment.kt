package pl.edu.zut.mad.schedulecalendar.ui

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tobishiba.circularviewpager.library.CircularViewPagerHandler
import kotlinx.android.synthetic.main.fragment_lessons_pager.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.*
import pl.edu.zut.mad.schedulecalendar.adapter.SchedulePagerAdapter
import pl.edu.zut.mad.schedulecalendar.model.Day
import java.util.*
import javax.inject.Inject


class LessonsPagerFragment : Fragment(),
        CalendarFragment.CalendarListener, AppBarLayout.OnOffsetChangedListener {

    companion object {
        private const val SELECTED_DATE = "selected_date"
        private const val EMPTY_TOOLBAR_TITLE = " "
        private const val COLLAPSED_PERCENT_SHOW_TITLE = 0.65f
        private const val FIRST_DAY_INDEX = 0
        private const val LAST_DAY_INDEX = 6
    }

    private lateinit var currentWeekDates: List<LocalDate>
    private lateinit var toolbarLayout: CollapsingToolbarLayout
    private var weekDaysNames: Array<String>? = null
    private var toolbar: Toolbar? = null
    private var calendarFragment: CalendarFragment? = null
    private var pagerAdapter: SchedulePagerAdapter? = null
    private var selectedDateString: String? = null
    private var isBarTitleShow = false
    private var barShowHeight = -1

    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.app.component.plus(ScheduleCalendarModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_lessons_pager, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        weekDaysNames = resources.getStringArray(R.array.week_days)
        initSelectedDateString(savedInstanceState)
        initPager()
        updateCurrentWeekDatesForSelectedDate()
        initLoader()
    }

    private fun initSelectedDateString(savedInstanceState: Bundle?) {
        selectedDateString = savedInstanceState?.getString(SELECTED_DATE) ?: DateUtils.convertDateToString(Date()) // TODO: replace with LocalDate
    }

    private fun initPager() {
        pagerAdapter = SchedulePagerAdapter(childFragmentManager)
        pagerView.adapter = pagerAdapter
        val pagerHandler = CircularViewPagerHandler(pagerView)
        pagerHandler.setOnPageChangeListener(onPageChangeListener)
        pagerView.addOnPageChangeListener(pagerHandler)
    }

    private val onPageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            if (position < FIRST_DAY_INDEX) {
                val date = currentWeekDates[FIRST_DAY_INDEX]
                updateCurrentWeekDatesForPrevWeek(date.toDate())
                moveToPrevMonthPageIfNeeded(date.toDate())
            } else if (position > LAST_DAY_INDEX) {
                val date = currentWeekDates[LAST_DAY_INDEX]
                updateCurrentWeekDatesForNextWeek(date.toDate())
                moveToNextMonthPageIfNeeded(date.toDate())
            } else {
                val date = currentWeekDates[position]
                calendarFragment?.setSelectedDate(date.toDate())
                selectedDateString = DateUtils.convertDateToShortString(date.toDate())
            }
        }
    }

    private fun updateCurrentWeekDatesForPrevWeek(date: Date) {
        currentWeekDates = DateUtils.getPrevWeekDatesFromDate(date)
        pagerAdapter?.setItems(currentWeekDates)
    }

    private fun moveToPrevMonthPageIfNeeded(date: Date) {
        if (calendarFragment!!.isFirstDateInMonthPage(date)) {
            calendarFragment?.prevMonth()
        }
    }

    private fun updateCurrentWeekDatesForNextWeek(date: Date) {
        currentWeekDates = DateUtils.getNextWeekDatesFromDate(date)
        pagerAdapter?.setItems(currentWeekDates)
    }

    private fun moveToNextMonthPageIfNeeded(date: Date) {
        if (calendarFragment!!.isLastDateInMonthPage(date)) {
            calendarFragment?.nextMonth()
        }
    }

    private fun updateCurrentWeekDatesForSelectedDate() {
        val date = DateUtils.convertStringToDate(selectedDateString)
        updateCurrentWeekDates(date)
    }

    private fun updateCurrentWeekDates(dayDate: Date?) {
        currentWeekDates = DateUtils.getWeekDates(dayDate)
        pagerAdapter?.setItems(currentWeekDates)
    }

    private fun initLoader() {
        val scheduleDays = scheduleRepository.getSchedule()
        if (!scheduleDays.isEmpty()) {
            calendarFragment!!.setClassesDates(getClassesDates(scheduleDays))
            selectCurrentDayPage()
            showSchedule()
        }
    }

    private fun getClassesDates(days: List<Day>): List<LocalDate> =// TODO: replace with repository method
            days.map { it.date!! }

    private fun selectCurrentDayPage() {
        val dayIndex = DateUtils.getDateDayIndex(selectedDateString)
        pagerView.currentItem = dayIndex + 1
    }

    private fun showSchedule() {
        contentView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAppBarLayout()
        toolbarLayout = activity.findViewById(R.id.collapsing_toolbar)
        toolbar = activity.findViewById(R.id.toolbar)
    }

    private fun initAppBarLayout() {
        val appBarLayout = activity.findViewById<AppBarLayout>(R.id.app_bar_layout)
        appBarLayout?.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val totalScrollRange = appBarLayout.totalScrollRange
        initBarShowHeightIfNeeded(totalScrollRange)
        enableOrDisableClickEventsOnCalendar(verticalOffset, totalScrollRange)
        showOrHideToolbarTitle(verticalOffset)
    }

    private fun initBarShowHeightIfNeeded(totalScrollRange: Int) {
        if (barShowHeight == -1) {
            barShowHeight = totalScrollRange
            barShowHeight = (barShowHeight * COLLAPSED_PERCENT_SHOW_TITLE).toInt()
        }
    }

    private fun enableOrDisableClickEventsOnCalendar(verticalOffset: Int, totalScrollRange: Int) {
        if (verticalOffset == 0) {
            toolbar!!.visibility = View.GONE
            calendarFragment!!.setEnabledForClickEvents(true)
        } else if (Math.abs(verticalOffset) >= totalScrollRange) {
            calendarFragment!!.setEnabledForClickEvents(false)
        }
    }

    private fun showOrHideToolbarTitle(verticalOffset: Int) {
        if (shouldShowTitle(verticalOffset)) {
            val dayName = getSelectedDayName()
            val toolbarTitle = dayName + " " + selectedDateString
            toolbarLayout.title = toolbarTitle
            isBarTitleShow = true
        } else if (isBarTitleShow) {
            toolbarLayout.title = EMPTY_TOOLBAR_TITLE
            isBarTitleShow = false
        }
    }

    private fun shouldShowTitle(verticalOffset: Int): Boolean = barShowHeight + verticalOffset <= 0

    private fun getSelectedDayName(): String {
        val dayIndex = DateUtils.getDateDayIndex(selectedDateString)
        return weekDaysNames!![dayIndex]
    }

    override fun onSelectDate(date: Date?) {
        selectedDateString = DateUtils.convertDateToShortString(date)
        updateCurrentWeekDatesIfNeeded(date!!)
        val dayIndex = DateUtils.getDateDayIndex(date)
        callViewPagerListener(dayIndex)
    }

    /**
     * When current item in [ViewPager] is the same as last selected date index,
     * [ViewPager.setCurrentItem] will not call
     * [android.support.v4.view.ViewPager.OnPageChangeListener] so we need to do it manually
     */
    private fun callViewPagerListener(dayIndex: Int) {
        val pageToSelect = dayIndex + 1
        if (pagerView.currentItem == pageToSelect) {
            onPageChangeListener.onPageSelected(dayIndex)
        }
        pagerView.setCurrentItem(pageToSelect, true)
    }

    private fun updateCurrentWeekDatesIfNeeded(dayDate: Date) {
        if (!isDayDateInCurrentWeek(dayDate)) {
            updateCurrentWeekDates(dayDate)
        }
    }

    private fun isDayDateInCurrentWeek(dayDate: Date): Boolean =
            currentWeekDates.any { it.compareTo(LocalDate.fromDateFields(dayDate)) == 0 }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SELECTED_DATE, selectedDateString)
    }

    fun registerCalendar(calendarFragment: Fragment) {
        if (calendarFragment is CalendarFragment) {
            this.calendarFragment = calendarFragment
            this.calendarFragment?.setCalendarListener(this)
        }
    }

    fun unregisterCalendar() {
        this.calendarFragment = null
    }
}
