package pl.edu.zut.mad.schedule

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

internal class SchedulePresenter(private val repository: ScheduleRepository, private val mapper: ModelMapper,
    private val view: ScheduleMvp.View, private val datesProvider: DatesProvider,
    private val user: User, private val connection: NetworkConnection) : ScheduleMvp.Presenter {

    override fun onViewIsCreated() =
        if (user.isSaved()) {
            view.showLoadingView() //TODO test
            loadLessons()
        } else {
            view.showLoginView()
        }

    override fun logout() {
        user.delete()
        repository.delete()
        view.showLoginView()
    }

    override fun refresh() =
        if (connection.isAvailable()) {
            view.refreshSchedule(user.getAlbumNumber())
        } else {
            view.showInternetError()
        }

    private fun loadLessons() {
        Observable.zip(repository.getScheduleMinDate(), repository.getScheduleMaxDate(), BiFunction<LocalDate, LocalDate, Observable<LocalDate>> { minDate, maxDate ->
            view.onDateIntervalCalculated(minDate, maxDate)
            Observable.fromIterable(datesProvider.getByInterval(minDate, maxDate))
        })
            .switchMap { it }
            .flatMap { repository.getDayByDate(it) }
            .map { mapper.toLessonsEvents(it) }
            .collect({ mutableListOf<CalendarEvent>() }, { allEvents, dayEvents -> allEvents.addAll(dayEvents) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.hideLoadingView() //TODO test
                    view.onLessonsEventsLoad(it) //TODO rename to setData
                },
                {}
            )
    }
}
