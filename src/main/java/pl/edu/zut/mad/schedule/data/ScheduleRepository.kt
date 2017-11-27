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

internal class ScheduleRepository(private val database: ScheduleDatabase, private val mapper: ModelMapper) {

    companion object {
        const val DATE_COLUMN = "date"
    }

    fun save(days: List<DayApi>): Completable =
        Completable.fromCallable {
            database.instance.executeTransaction({ it.copyToRealm(days) })
        }

    fun delete(): Disposable =
        Observable.fromCallable { database.instance.executeTransaction { it.deleteAll() } }
            .subscribeOn(Schedulers.io())
            .subscribe()

    fun getDayByDate(date: LocalDate): Observable<OptionalDay> {
        val day = database.instance.where(DayApi::class.java)
            .equalTo(DATE_COLUMN, date.toDate())
            .findFirst()
        val optionalDay: OptionalDay =
            if (day == null) EmptyDay(date) else mapper.toDayUiFromApi(day)
        return Observable.just(optionalDay)
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
