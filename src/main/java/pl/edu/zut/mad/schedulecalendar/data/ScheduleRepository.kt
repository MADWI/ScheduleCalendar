package pl.edu.zut.mad.schedulecalendar.data

import io.realm.Realm
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedulecalendar.util.ModelMapper
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day as DayDb
import pl.edu.zut.mad.schedulecalendar.data.model.db.Lesson as LessonDb
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedulecalendar.data.model.ui.Lesson as LessonUi


class ScheduleRepository(private val database: Realm, private val mapper: ModelMapper) {

    fun saveSchedule(scheduleDays: List<DayDb>, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        database.executeTransactionAsync(
                { it.copyToRealm(scheduleDays) },
                { onSuccess() },
                { onError(it) }
        )
    }

    fun deleteSchedule() {
        database.executeTransaction { database.deleteAll() }
    }

    // TODO change to async
    fun getLessonsForDay(dayDate: LocalDate): DayUi? {
        val dayDb = database.where(DayDb::class.java)
                .equalTo("date", mapper.toStringFromDate(dayDate))
                .findFirst() ?: return null
        return mapper.dayFromDbToUi(dayDb)
    }

    fun getSchedule() = database.where(DayDb::class.java).findAll().map { mapper.dayFromDbToUi(it) }
}
