package pl.edu.zut.mad.schedulecalendar

import io.realm.Realm
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.model.Day
import pl.edu.zut.mad.schedulecalendar.model.Schedule
import pl.edu.zut.mad.schedulecalendar.network.ScheduleEdzLoader
import java.util.*


class ScheduleRepository {

    companion object {
        private val database = Realm.getDefaultInstance()
    }

    // TODO: prevent from multiple Schedule in db
    fun saveSchedule(schedule: Schedule) {
        database.beginTransaction()
        database.copyToRealm(schedule)
        database.commitTransaction()
    }

    fun saveSchedule(content: String) {
        val loader = ScheduleEdzLoader()
        val schedule = loader.parseData(content)
        saveSchedule(schedule)
    }

    fun getSchedule(): Schedule? = database.where(Schedule::class.java).findFirst() // change to async

    fun getDayScheduleByDate(date: Date): Day? { // change to async
        val dateTime = LocalDate.fromDateFields(date).toString()
        return database.where(Day::class.java).equalTo("time", dateTime).findFirst()
    }
}
