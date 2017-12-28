package pl.edu.zut.mad.schedule.data

import io.reactivex.Completable
import io.reactivex.Observable
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
            val instance = database.instance
            instance.executeTransaction { it.copyToRealm(days) }
            instance.close()
        }

    fun delete() {
        val instance = database.instance
        instance.executeTransactionAsync { it.deleteAll() }
        instance.close()
    }

    fun getDayByDate(date: LocalDate): Observable<OptionalDay> {
        val instance = database.instance
        val day = instance.where(DayApi::class.java)
            .equalTo(DATE_COLUMN, date.toDate())
            .findFirst()
        val optionalDay: OptionalDay =
            if (day == null) EmptyDay(date) else mapper.toDayUiFromApi(day)
        instance.close()
        return Observable.just(optionalDay)
    }

    fun getScheduleMinDate(): LocalDate {
        val instance = database.instance
        val date = instance.where(DayApi::class.java)
            .minimumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }
        instance.close()
        return date
    }

    fun getScheduleMaxDate(): LocalDate {
        val instance = database.instance
        val date = instance
            .where(DayApi::class.java)
            .maximumDate(DATE_COLUMN)
            .let { mapper.toUiDate(it) }
        instance.close()
        return date
    }
}
