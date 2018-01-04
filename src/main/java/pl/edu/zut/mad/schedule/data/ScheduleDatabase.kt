package pl.edu.zut.mad.schedule.data

import io.realm.Realm
import pl.edu.zut.mad.schedule.data.model.api.Day
import java.util.Date

internal class ScheduleDatabase {

    companion object {
        const val DATE_COLUMN = "date"
    }

    fun save(days: List<Day>) {
        val database = getDatabase()
        database.executeTransaction { it.copyToRealm(days) }
        database.close()
    }

    fun delete() {
        val database = getDatabase()
        database.executeTransactionAsync { it.deleteAll() }
        database.close()
    }

    fun findDayWithDate(date: Date): Day? {
        val database = getDatabase()
        var day: Day? = database.where(Day::class.java)
            .equalTo(DATE_COLUMN, date)
            .findFirst() ?: return null
        day = database.copyFromRealm(day)
        database.close()
        return day
    }

    fun findMinimumDate(): Date? {
        val database = getDatabase()
        val date = database.where(Day::class.java).minimumDate(DATE_COLUMN)
        database.close()
        return date
    }

    fun findMaximumDate(): Date? {
        val database = getDatabase()
        val date = database.where(Day::class.java).maximumDate(DATE_COLUMN)
        database.close()
        return date
    }

    // TODO inject configuration with migration
    private fun getDatabase() = Realm.getDefaultInstance()
}
