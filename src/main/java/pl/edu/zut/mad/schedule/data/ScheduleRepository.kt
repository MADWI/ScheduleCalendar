package pl.edu.zut.mad.schedule.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.OptionalDay
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi

internal class ScheduleRepository(private val mapper: ModelMapper) {

    companion object {
        const val DATE_COLUMN = "date"
    }

    fun save(days: List<DayApi>): Completable =
        Completable.fromCallable {
            val database = Realm.getDefaultInstance()
            database.executeTransaction({ it.copyToRealm(days) })
            database.close()
        }

    fun delete(): Disposable = //TODO: check remove rx
        Observable.fromCallable {
            val database = Realm.getDefaultInstance()
            database.executeTransaction { it.deleteAll() }
            database.close()
        }
            .subscribeOn(Schedulers.io())
            .subscribe()

    fun getDayByDate(date: LocalDate): Observable<OptionalDay> {
        val database = Realm.getDefaultInstance()
        val day = database.where(DayApi::class.java)
            .equalTo(DATE_COLUMN, date.toDate())
            .findFirst()
        val optionalDay: OptionalDay =
            if (day == null) EmptyDay(date) else mapper.toDayUiFromApi(day)
        database.close()
        return Observable.just(optionalDay)
    }

    fun getScheduleMinDate(): LocalDate {
        val database = Realm.getDefaultInstance()
        val date = database.where(DayApi::class.java)
            .minimumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }
        database.close()
        return date
    }

    fun getScheduleMaxDate(): LocalDate {
        val database = Realm.getDefaultInstance()
        val date = database.where(DayApi::class.java)
            .maximumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }
        database.close()
        return date
    }
}
