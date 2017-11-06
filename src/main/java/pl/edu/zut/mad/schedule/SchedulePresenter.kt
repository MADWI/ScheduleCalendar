package pl.edu.zut.mad.schedule

import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import io.reactivex.Observable
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection
import pl.edu.zut.mad.schedule.data.model.api.Day as DayDb
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi

internal class SchedulePresenter(private val repository: ScheduleRepository, private val mapper: ModelMapper,
                        private val view: ScheduleMvp.View, private val datesProvider: DatesProvider,
                        private val user: User, private val connection: NetworkConnection)
    : ScheduleMvp.Presenter {

    override fun logout() {
        user.delete()
        repository.delete()
        view.showLoginView(null)
    }

    override fun refresh() =
            if (connection.isAvailable()) {
                view.showLoginView(user.getAlbumNumber())
            } else {
                view.showInternetError()
            }

    override fun loadData() =
            if (user.isSaved()) {
                loadLessons()
            } else {
                view.showLoginView(null)
            }

    private fun loadLessons() {
        val minDate = repository.getScheduleMinDate()
        val maxDate = repository.getScheduleMaxDate()
        view.onDateIntervalCalculated(minDate, maxDate)

        val events: MutableList<CalendarEvent> = ArrayList()
        val dateDates = datesProvider.getByInterval(minDate, maxDate)
        Observable.fromIterable(dateDates)
                .flatMap { repository.getDaysForDate(it) }
                .subscribe(
                        {
                            when (it) {
                                is DayUi -> events.addAll(mapper.toLessonsEvents(it))
                                is EmptyDay -> events.add(mapper.toLessonEvent(it))
                            }
                        },
                        { },
                        { view.onLessonsEventLoad(events) }
                )
    }
}
