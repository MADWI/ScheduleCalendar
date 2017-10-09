package pl.edu.zut.mad.schedulecalendar.model;


import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Day extends RealmObject {

    private RealmList<Lesson> lessons;
    private String time;

    public Day() {
    }

    public Day(LocalDate date, List<Lesson> lessons) {
        this.lessons = new RealmList<>();
        this.lessons.addAll(lessons);
        this.time = date.toString();
    }

    public Date getDate() {
        LocalDate date = LocalDate.parse(time);
        return date.toDate();
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
