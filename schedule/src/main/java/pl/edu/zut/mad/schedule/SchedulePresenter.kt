package pl.edu.zut.mad.schedule

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.model.ui.LessonEvent
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

internal class SchedulePresenter(private val repository: ScheduleRepository, private val mapper: ModelMapper,
    private val view: ScheduleMvp.View, private val datesProvider: DatesProvider,
    private val user: User, private val connection: NetworkConnection) : ScheduleMvp.Presenter {

    override fun onViewIsCreated() =
        if (user.isSaved()) {
            view.showLoadingView()
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
        val minDateObservable = repository.getScheduleMinDate()
        val maxDateObservable = repository.getScheduleMaxDate()
        Observable.zip(minDateObservable, maxDateObservable, getZipperForMinAndMaxDatesPair())
            .doOnNext { view.onDateIntervalCalculated(it.first, it.second) }
            .flatMapIterable { datesProvider.getByInterval(it.first, it.second) }
            .flatMap { repository.getDayByDate(it) }
            .map { mapper.toLessonsEvents(it) }
            .collect({ mutableListOf<LessonEvent>() }, { allEvents, dayEvents -> allEvents.addAll(dayEvents) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.hideLoadingView()
                    view.setData(it)
                },
                {}
            )
    }

    /**
     * This cannot be converted to lambda expression due to compile error
     * @see <a href="https://youtrack.jetbrains.com/issue/KT-13609">Kotlin issue</a>
     */
    private fun getZipperForMinAndMaxDatesPair() =
        BiFunction<LocalDate, LocalDate, Pair<LocalDate, LocalDate>> { minDate, maxDate ->
            Pair(minDate, maxDate)
        }
}
