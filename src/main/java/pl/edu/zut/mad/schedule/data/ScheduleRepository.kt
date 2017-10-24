package pl.edu.zut.mad.schedule.data

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.model.db.Day as DayDb
import pl.edu.zut.mad.schedule.data.model.db.Lesson as LessonDb
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi


class ScheduleRepository(private val database: Realm, private val mapper: ModelMapper) {

    fun save(days: List<DayDb>): Observable<*> =
            Observable.fromCallable { database.executeTransactionAsync({ it.copyToRealm(days) }) }
                    .subscribeOn(Schedulers.single())

    fun delete(): Disposable =
            Observable.fromCallable { database.executeTransaction { database.deleteAll() } }
                    .subscribeOn(Schedulers.single())
                    .subscribe()

    // TODO change to async
    fun getLessonsForDay(dayDate: LocalDate): DayUi? {
//        val dayDb = database.where(DayDb::class.java)
//                .equalTo("date", mapper.toStringFromDate(dayDate))
//                .findFirst() ?: return null
//        return mapper.dayFromDbToUi(dayDb)
        return null
    }

    fun getSchedule() = database.where(DayDb::class.java).findAll().map { mapper.dayFromDbToUi(it) }
}
