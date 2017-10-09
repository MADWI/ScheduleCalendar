package pl.edu.zut.mad.schedulecalendar

import io.realm.Realm
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.model.Day
import java.util.*


class ScheduleRepository {

    companion object {
        private val database = Realm.getDefaultInstance()
    }

    // TODO: change to async
    fun saveSchedule(scheduleDays: List<Day>) {
        database.beginTransaction()
        database.copyToRealm(scheduleDays)
        database.commitTransaction()
    }

    fun saveSchedule(content: String) {
        database.beginTransaction()
        database.deleteAll()
        database.commitTransaction()
        val scheduleDays = ScheduleParser().parseData(content)
        saveSchedule(scheduleDays)
    }

    fun getSchedule(): List<Day> = database.where(Day::class.java).findAll() // change to async

    fun getDayScheduleByDate(date: Date): Day? { // change to async
        val dateTime = LocalDate.fromDateFields(date).toString()
        return database.where(Day::class.java).equalTo("time", dateTime).findFirst()
    }
}
