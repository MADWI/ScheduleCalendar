package pl.edu.zut.mad.schedulecalendar.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tobishiba.circularviewpager.library.CircularViewPagerHandler;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.edu.zut.mad.schedulecalendar.DateUtils;
import pl.edu.zut.mad.schedulecalendar.R;
import pl.edu.zut.mad.schedulecalendar.ScheduleRepository;
import pl.edu.zut.mad.schedulecalendar.adapter.SchedulePagerAdapter;
import pl.edu.zut.mad.schedulecalendar.model.Day;
import pl.edu.zut.mad.schedulecalendar.ui.CalendarFragment;

class SchedulePagerFragment extends Fragment
        implements CalendarFragment.CalendarListener, AppBarLayout.OnOffsetChangedListener {

    private static final String SELECTED_DATE = "selected_date";
    private static final String EMPTY_TOOLBAR_TITLE = " ";
    private static final float COLLAPSED_PERCENT_SHOW_TITLE = 0.65f;
    private static final int FIRST_DAY_INDEX = 0;
    private static final int LAST_DAY_INDEX = 6;
    private View scheduleWrapper;
    private ViewPager viewPager;
    private ProgressBar loadingIndicator;
    private String[] weekDaysNames;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private CalendarFragment calendarFragment;
    private SchedulePagerAdapter pagerAdapter;
    private List<LocalDate> currentWeekDates;
    private String selectedDateString;
    private boolean isBarTitleShow = false;
    private int barShowHeight = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule_main, container, false);
        init(rootView, savedInstanceState);
        return rootView;
    }

    private void init(View view, Bundle savedInstanceState) {
        scheduleWrapper = view.findViewById(R.id.schedule_main);
        viewPager = view.findViewById(R.id.pager);
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        weekDaysNames = getResources().getStringArray(R.array.week_days);
        readSavedSelectedDateString(savedInstanceState);
        initPager();
        updateCurrentWeekDatesForSelectedDate();
        initLoader();
    }

    private void readSavedSelectedDateString(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            selectedDateString = savedInstanceState.getString(SELECTED_DATE);
        } else {
            selectedDateString = DateUtils.convertDateToString(new Date());
        }
    }

    private void initPager() {
        pagerAdapter = new SchedulePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        CircularViewPagerHandler pagerHandler = new CircularViewPagerHandler(viewPager);
        pagerHandler.setOnPageChangeListener(onPageChangeListener);
        viewPager.addOnPageChangeListener(pagerHandler);
    }

    private final ViewPager.SimpleOnPageChangeListener onPageChangeListener
            = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (position < FIRST_DAY_INDEX) {
                LocalDate date = currentWeekDates.get(FIRST_DAY_INDEX);
                updateCurrentWeekDatesForPrevWeek(date.toDate());
                moveToPrevMonthPageIfNeeded(date.toDate());
            } else if (position > LAST_DAY_INDEX) {
                LocalDate date = currentWeekDates.get(LAST_DAY_INDEX);
                updateCurrentWeekDatesForNextWeek(date.toDate());
                moveToNextMonthPageIfNeeded(date.toDate());
            } else {
                LocalDate date = currentWeekDates.get(position);
                calendarFragment.setSelectedDate(date.toDate());
                selectedDateString = DateUtils.convertDateToShortString(date.toDate());
            }
        }
    };

    private void updateCurrentWeekDatesForPrevWeek(@NonNull Date date) {
        currentWeekDates = DateUtils.getPrevWeekDatesFromDate(date);
        pagerAdapter.setItems(currentWeekDates);
    }

    private void moveToPrevMonthPageIfNeeded(@NonNull Date date) {
        if (calendarFragment.isFirstDateInMonthPage(date)) {
            calendarFragment.prevMonth();
        }
    }

    private void updateCurrentWeekDatesForNextWeek(@NonNull Date date) {
        currentWeekDates = DateUtils.getNextWeekDatesFromDate(date);
        pagerAdapter.setItems(currentWeekDates);
    }

    private void moveToNextMonthPageIfNeeded(@NonNull Date date) {
        if (calendarFragment.isLastDateInMonthPage(date)) {
            calendarFragment.nextMonth();
        }
    }

    private void updateCurrentWeekDatesForSelectedDate() {
        Date date = DateUtils.convertStringToDate(selectedDateString);
        updateCurrentWeekDates(date);
    }

    private void updateCurrentWeekDates(@Nullable Date dayDate) {
        currentWeekDates = DateUtils.getWeekDates(dayDate);
        pagerAdapter.setItems(currentWeekDates);
    }

    private void initLoader() {
        List<Day> scheduleDays = new ScheduleRepository().getSchedule();
        if (!scheduleDays.isEmpty()) {
            calendarFragment.setClassesDates(getClassesDates(scheduleDays));
            selectCurrentDayPage();
            showSchedule();
        }
    }

    public List<LocalDate> getClassesDates(List<Day> days) { // TODO: replace with repository method
        List<LocalDate> classesDates = new ArrayList<>();
        for (Day day : days) {
            classesDates.add(day.getDate());
        }
        return classesDates;
    }

    private void selectCurrentDayPage() {
        int dayIndex = DateUtils.getDateDayIndex(selectedDateString);
        viewPager.setCurrentItem(dayIndex + 1);
    }

    private void showSchedule() {
        scheduleWrapper.setVisibility(View.VISIBLE);
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAppBarLayout();
        toolbarLayout = getActivity().findViewById(R.id.collapsing_toolbar);
        toolbar = getActivity().findViewById(R.id.toolbar);
    }

    private void initAppBarLayout() {
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.app_bar_layout);
        if (appBarLayout != null) {
            appBarLayout.addOnOffsetChangedListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        initBarShowHeightIfNeeded(totalScrollRange);
        enableOrDisableClickEventsOnCalendar(verticalOffset, totalScrollRange);
        showOrHideToolbarTitle(verticalOffset);
    }

    private void initBarShowHeightIfNeeded(int totalScrollRange) {
        if (barShowHeight == -1) {
            barShowHeight = totalScrollRange;
            barShowHeight *= COLLAPSED_PERCENT_SHOW_TITLE;
        }
    }

    private void enableOrDisableClickEventsOnCalendar(int verticalOffset, int totalScrollRange) {
        if (verticalOffset == 0) {
            toolbar.setVisibility(View.GONE);
            calendarFragment.setEnabledForClickEvents(true);
        } else if (Math.abs(verticalOffset) >= totalScrollRange) {
            calendarFragment.setEnabledForClickEvents(false);
        }
    }

    private void showOrHideToolbarTitle(int verticalOffset) {
        if (shouldShowTitle(verticalOffset)) {
            String dayName = getSelectedDayName();
            String toolbarTitle = dayName + " " + selectedDateString;
            toolbarLayout.setTitle(toolbarTitle);
            isBarTitleShow = true;
        } else if (isBarTitleShow) {
            toolbarLayout.setTitle(EMPTY_TOOLBAR_TITLE);
            isBarTitleShow = false;
        }
    }

    private boolean shouldShowTitle(int verticalOffset) {
        return barShowHeight + verticalOffset <= 0;
    }

    private String getSelectedDayName() {
        int dayIndex = DateUtils.getDateDayIndex(selectedDateString);
        return weekDaysNames[dayIndex];
    }

    @Override
    public void onSelectDate(Date date) {
        selectedDateString = DateUtils.convertDateToShortString(date);
        updateCurrentWeekDatesIfNeeded(date);
        int dayIndex = DateUtils.getDateDayIndex(date);
        callViewPagerListener(dayIndex);
    }

    /**
     * When current item in {@link ViewPager} is the same as last selected date index,
     * {@link ViewPager#setCurrentItem(int)} will not call
     * {@link android.support.v4.view.ViewPager.OnPageChangeListener} so we need to do it manually
     */
    private void callViewPagerListener(int dayIndex) {
        int pageToSelect = dayIndex + 1;
        if (viewPager.getCurrentItem() == pageToSelect) {
            onPageChangeListener.onPageSelected(dayIndex);
        }
        viewPager.setCurrentItem(pageToSelect, true);
    }

    private void updateCurrentWeekDatesIfNeeded(@NonNull Date dayDate) {
        if (!isDayDateInCurrentWeek(dayDate)) {
            updateCurrentWeekDates(dayDate);
        }
    }

    private boolean isDayDateInCurrentWeek(@NonNull Date dayDate) {
        for (LocalDate date : currentWeekDates) {
            if (date.compareTo(LocalDate.fromDateFields(dayDate)) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_DATE, selectedDateString);
    }

    public void registerCalendar(Fragment calendarFragment) {
        if (calendarFragment instanceof CalendarFragment) {
            this.calendarFragment = (CalendarFragment) calendarFragment;
            this.calendarFragment.setCalendarListener(this);
        }
    }

    public void unregisterCalendar() {
        this.calendarFragment = null;
    }
}
