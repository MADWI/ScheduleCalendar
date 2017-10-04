package pl.edu.zut.mad.schedulecalendar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScheduleFragment extends Fragment {

    private static final String CALENDAR_TAG = "calendar_fragment";
    private static final String SCHEDULE_TAG = "schedule_fragment";
    private static final String ABOUT_US_TAG = "about_us_fragment";
    private CalendarFragment calendarFragment;
    private SchedulePagerFragment schedulePagerFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initScheduleFragments(savedInstanceState);
    }

    private void initScheduleFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            calendarFragment = new CalendarFragment();
            schedulePagerFragment = new SchedulePagerFragment();
            startScheduleFragments();
        } else {
            initScheduleFragmentsFromStack();
        }
        registerCalendarForScheduleFragment();
    }

    private void startScheduleFragments() {
        replaceFragmentInViewContainer(calendarFragment, R.id.calendar_container, CALENDAR_TAG);
        replaceFragmentInViewContainer(schedulePagerFragment, R.id.schedule_container, SCHEDULE_TAG);
    }

    private void replaceFragmentInViewContainer(Fragment fragment, int containerId, String tag) {
        getFragmentManager().beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();
    }

    private void initScheduleFragmentsFromStack() {
        calendarFragment = (CalendarFragment) getFragmentFromStackWithTag(CALENDAR_TAG);
        schedulePagerFragment = (SchedulePagerFragment) getFragmentFromStackWithTag(SCHEDULE_TAG);
    }

    private Fragment getFragmentFromStackWithTag(String tag) {
        return getFragmentManager().findFragmentByTag(tag);
    }

    private void registerCalendarForScheduleFragment() {
        if (schedulePagerFragment != null) {
            schedulePagerFragment.registerCalendar(calendarFragment);
        }
    }

}
