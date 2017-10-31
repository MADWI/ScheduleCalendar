package pl.edu.zut.mad.schedule.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.OptionalDay
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.api.Lesson as LessonApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi
import pl.edu.zut.mad.schedule.data.model.ui.Lesson as LessonUi


internal class ScheduleRepository(private val database: ScheduleDatabase, private val mapper: ModelMapper) {

    companion object {
        private const val DATE_COLUMN = "date"
    }

    fun save(days: List<DayApi>): Completable =
            Completable.fromCallable {
                database.instance.executeTransaction({ it.copyToRealm(days) })
            }

    fun delete(): Disposable =
            Observable.fromCallable { database.instance.executeTransaction { it.deleteAll() } }
                    .subscribeOn(Schedulers.io())
                    .subscribe()

    fun getDaysForDate(date: LocalDate): Observable<OptionalDay> =
            Observable.fromCallable<OptionalDay> {
                database.instance.where(DayApi::class.java)
                        .equalTo(DATE_COLUMN, date.toDate())
                        .findFirst()
                        ?.asFlowable<DayApi>()
                        ?.map { mapper.dayFromApiToUi(it) }
                        ?.blockingFirst() ?: EmptyDay(date)
            }

    fun getScheduleMinDate(): LocalDate = database.instance
            .where(DayApi::class.java)
            .minimumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }

    fun getScheduleMaxDate(): LocalDate = database.instance
            .where(DayApi::class.java)
            .maximumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }
}
