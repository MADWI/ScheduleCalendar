package pl.edu.zut.mad.schedulecalendar.data

import io.realm.Realm
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.util.ModelMapper
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day as DayDb
import pl.edu.zut.mad.schedulecalendar.data.model.db.Lesson as LessonDb
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Lesson as LessonUi


class ScheduleRepository(private val database: Realm, private val mapper: ModelMapper) {

    // TODO: change to async
    fun saveSchedule(scheduleDays: List<DayDb>) {
        database.beginTransaction()
        database.copyToRealm(scheduleDays)
        database.commitTransaction()
    }

    // TODO: move mapping to presenter
    fun getScheduleDayDates(): List<LocalDate> =
            database.where(DayDb::class.java)
                    .findAll()
                    .map { mapper.toDateFromString(it.date) }

    fun getDayByDate(date: LocalDate): DayUi? {
        // TODO change to async
        val dayDb = database.where(DayDb::class.java)
                .equalTo("date", mapper.toStringFromDate(date))
                .findFirst() ?: return null
        return mapper.dayFromDbToUi(dayDb)
    }
}
