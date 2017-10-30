package pl.edu.zut.mad.schedule.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.OptionalDay
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.model.db.Day as DayDb // TODO: Db -> Api
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

    fun getLessonsForDay(dayDate: LocalDate): Observable<OptionalDay> =
            Observable.fromCallable<OptionalDay> {
                database.instance.where(DayDb::class.java)
                        .equalTo(DATE_COLUMN, dayDate.toDate())
                        .findFirst()
                        ?.asFlowable<DayDb>()
                        ?.map { mapper.dayFromDbToUi(it) }
                        ?.blockingFirst() ?: EmptyDay(dayDate)
            }

    fun getScheduleMinDate(): LocalDate = database.instance
            .where(DayDb::class.java)
            .minimumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }

    fun getScheduleMaxDate(): LocalDate = database.instance
            .where(DayDb::class.java)
            .maximumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }
}
