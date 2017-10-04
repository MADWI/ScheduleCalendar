package pl.edu.zut.mad.schedulecalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.edu.zut.mad.schedulecalendar.adapter.ScheduleDayAdapter;
import pl.edu.zut.mad.schedulecalendar.network.BaseDataLoader;
import pl.edu.zut.mad.schedulecalendar.network.DataLoadingManager;
import pl.edu.zut.mad.schedulecalendar.network.ScheduleEdzLoader;

public class ScheduleDayFragment extends Fragment implements BaseDataLoader.DataLoadedListener<Schedule> {

    private static final String DATE_KEY = "date";
    private ScheduleDayAdapter adapter;
    private Date date;
    private Schedule schedule;
    private BaseDataLoader<Schedule, ?> loader;
    private RecyclerView classesListView;
    private View noClassesImageView;
    private View noClassesMessageView;

    public static ScheduleDayFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putLong(DATE_KEY, date.getTime());

        ScheduleDayFragment fragment = new ScheduleDayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle retainedArguments = getArguments();
        if (retainedArguments != null) {
            date = new Date(retainedArguments.getLong(DATE_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_day, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        initViews(view);
        initListViewWithAdapter();
        initLoader();
    }

    private void initViews(View view) {
        classesListView = view.findViewById(R.id.classesListView);
        noClassesImageView =view.findViewById(R.id.noClassesImageView);
        noClassesMessageView = view.findViewById(R.id.noClassesTextView);
    }

    private void initListViewWithAdapter() {
        adapter = new ScheduleDayAdapter(getContext());
        classesListView.setAdapter(adapter);
    }

    private void initLoader() {
        loader = DataLoadingManager
                .getInstance(getContext())
                .getLoader(ScheduleEdzLoader.class);
        loader.registerAndLoad(this);
    }

    @Override
    public void onDataLoaded(Schedule schedule) {
        this.schedule = schedule;
        if (schedule != null) {
            putDataInView();
        }
    }

    private void putDataInView() {
        if (schedule == null) {
            return;
        }

        Schedule.Day scheduleDay = schedule.getScheduleForDate(date);
        if (scheduleDay == null) {
            noClassesMessageView.setVisibility(View.VISIBLE);
            noClassesImageView.setVisibility(View.VISIBLE);
            adapter.setHoursInDay(null);
        } else {
            List<Schedule.Hour> hoursInDay = Arrays.asList(scheduleDay.getTasks());
            adapter.setHoursInDay(hoursInDay);
            noClassesMessageView.setVisibility(View.GONE);
            noClassesImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (date != null) {
            outState.putLong(DATE_KEY, date.getTime());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loader.unregister(this);
    }
}
