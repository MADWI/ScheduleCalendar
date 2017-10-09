package pl.edu.zut.mad.schedulecalendar;

import android.util.Log;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.zut.mad.schedulecalendar.model.Day;
import pl.edu.zut.mad.schedulecalendar.model.Lesson;
import pl.edu.zut.mad.schedulecalendar.model.TimeRange;

class ScheduleParser {

    private static final String TAG = "ScheduleParser";
    private static final Pattern HOUR_PATTERN = Pattern.compile("(\\d\\d):(\\d\\d)");

    public List<Day> parseData(String data) throws JSONException {
        JSONArray table = new JSONArray(data);

        LocalDate forDay = null;
        List<Lesson> dayLessons = new ArrayList<>();
        List<Day> days = new ArrayList<>();

        // Start from 1 - skip header
        for (int i = 1; i < table.length(); i++) {
            JSONArray row = table.getJSONArray(i);
            if (row.length() == 1) {
                // Header
                // Finish currently built day
                if (forDay != null) {
                    days.add(new Day(forDay, dayLessons));
                    dayLessons.clear();
                }

                // Start next day
                forDay = LocalDate.parse(row.getString(0).split(",")[0]);
            } else if (row.length() == 10 && forDay != null) {
                // Actual row
                Matcher startHour = HOUR_PATTERN.matcher(row.getString(2));
                Matcher endHour = HOUR_PATTERN.matcher(row.getString(3));
                if (!startHour.find() || !endHour.find()) {
                    throw new JSONException("Unable to match lesson");
                }
                Lesson lesson = new Lesson(
                        row.getString(4), // name
                        row.getString(8), // type
                        row.getString(6), // room
                        row.getString(5), // teacher
                        new TimeRange(
                                Integer.parseInt(startHour.group(1)),
                                Integer.parseInt(startHour.group(2)),
                                Integer.parseInt(endHour.group(1)),
                                Integer.parseInt(endHour.group(2))
                        )
                );
                dayLessons.add(lesson);
            } else {
                Log.e(TAG, "Unrecognized row");
            }
        }

        // Add last day to days
        days.add(new Day(forDay, dayLessons));

        return days;
    }
}
