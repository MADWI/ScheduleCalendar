package pl.edu.zut.mad.schedule.data

import io.reactivex.Completable
import io.reactivex.Observable
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.OptionalDay
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi

internal class ScheduleRepository(private val database: ScheduleDatabase, private val mapper: ModelMapper) {

    fun save(days: List<DayApi>): Completable =
        Completable.fromCallable {
            database.save(days)
        }

    fun delete() = database.delete()

    fun getDayByDate(date: LocalDate): Observable<OptionalDay> {
        val day = database.findDayByDate(date.toDate())
        val optionalDay: OptionalDay =
            if (day == null) EmptyDay(date) else mapper.toDayUiFromApi(day)
        return Observable.just(optionalDay)
    }

    fun getScheduleMinDate(): LocalDate {
        val date = database.findMinimumDate()
        return mapper.toUiDate(date)
    }

    fun getScheduleMaxDate(): LocalDate {
        val date = database.findMaximumDate()
        return mapper.toUiDate(date)
    }
}
