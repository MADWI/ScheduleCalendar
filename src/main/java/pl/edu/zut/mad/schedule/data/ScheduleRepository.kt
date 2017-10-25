package pl.edu.zut.mad.schedule.data

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.model.db.Day as DayDb
import pl.edu.zut.mad.schedule.data.model.db.Lesson as LessonDb
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi


class ScheduleRepository(private val database: ScheduleDatabase, private val mapper: ModelMapper) {

    companion object {
        private const val DATE_COLUMN = "date"
    }

    fun save(days: List<DayDb>): Completable =
            Completable.fromCallable {
                database.instance.executeTransaction({ it.copyToRealm(days) })
            }

    fun delete(): Disposable =
            Observable.fromCallable { database.instance.executeTransaction { it.deleteAll() } }
                    .subscribeOn(Schedulers.io())
                    .subscribe()

    fun getLessonsForDay(dayDate: LocalDate): Maybe<DayUi> = Maybe.fromCallable<DayUi> {
        database.instance.where(DayDb::class.java)
                .equalTo(DATE_COLUMN, mapper.toStringFromDate(dayDate))
                .findFirst()
                ?.asFlowable<DayDb>() // TODO: change to calls without Rx?
                ?.map {
                    mapper.dayFromDbToUi(it)
                }
                ?.blockingFirst()
    }

    fun getSchedule() = database.instance.where(DayDb::class.java).findAll().map { mapper.dayFromDbToUi(it) } // TODO
}
