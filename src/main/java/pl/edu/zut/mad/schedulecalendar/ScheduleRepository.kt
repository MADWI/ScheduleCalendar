package pl.edu.zut.mad.schedulecalendar

import io.realm.Realm
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.model.Day


class ScheduleRepository(private val database: Realm) {

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

    fun getSchedule(): List<Day> = database.where(Day::class.java).findAll() // TODO change to async

    fun getDayByDate(date: LocalDate): Day? =// TODO change to async
            database.where(Day::class.java).equalTo("time", date.toString()).findFirst()
}
