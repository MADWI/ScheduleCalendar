package pl.edu.zut.mad.schedule

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import io.reactivex.Observable
import org.joda.time.LocalDate
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.model.ui.Day
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

@RunWith(DataProviderRunner::class)
internal class SchedulePresenterTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    val user = mock<User>()
    val mapper = mock<ModelMapper>()
    val view = mock<ScheduleMvp.View>()
    val datesProvider = mock<DatesProvider>()
    val connection = mock<NetworkConnection>()
    val repository = mock<ScheduleRepository>()

    @InjectMocks
    private lateinit var schedulePresenter: SchedulePresenter

    companion object {
        private val DATE = LocalDate.now()
        private val MIN_DATE = LocalDate.now()
        private val MAX_DATE = MIN_DATE.plusMonths(1)
    }

    @Test
    fun getDayByDateIsCalledOnViewIsCreated() {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()

        schedulePresenter.onViewIsCreated()

        verify(repository).getDayByDate(DATE)
    }

    @Test
    @UseDataProvider("dayUi", location = arrayOf(MockData::class))
    fun onLessonsEventsLoadIsCalledWhenRepositoryReturnsDay(day: Day) {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()
        whenever(repository.getDayByDate(DATE)).thenReturn(Observable.just(day))

        schedulePresenter.onViewIsCreated()

        verify(view).onLessonsEventsLoad(any())
    }

    @Test
    @UseDataProvider("emptyDay", location = arrayOf(MockData::class))
    fun onLessonsEventsLoadIsCalledWhenRepositoryReturnsEmptyDay(emptyDay: EmptyDay) {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()
        whenever(repository.getDayByDate(DATE)).thenReturn(Observable.just(emptyDay))

        schedulePresenter.onViewIsCreated()

        verify(view).onLessonsEventsLoad(any())
    }

    @Test
    fun onDateIntervalCalculatedIsCalledOnViewIsCreated() {
        whenever(user.isSaved()).thenReturn(true)
        whenever(repository.getScheduleMinDate()).thenReturn(MIN_DATE)
        whenever(repository.getScheduleMaxDate()).thenReturn(MAX_DATE)

        schedulePresenter.onViewIsCreated()

        verify(view).onDateIntervalCalculated(MIN_DATE, MAX_DATE)
    }

    @Test
    fun showLoginViewIsCalledWhenUserIsNotSaved() {
        whenever(user.isSaved()).thenReturn(false)

        schedulePresenter.onViewIsCreated()

        verify(view).showLoginView()
    }

    @Test
    fun refreshScheduleIsCalledWhenConnectionIsAvailable() {
        whenever(connection.isAvailable()).thenReturn(true)

        schedulePresenter.refresh()

        verify(view).refreshSchedule(any())
    }

    @Test
    fun showInternetErrorIsCalledWhenConnectionIsAvailable() {
        whenever(connection.isAvailable()).thenReturn(false)

        schedulePresenter.refresh()

        verify(view).showInternetError()
    }

    @Test
    fun userDeleteIsCalledOnLogout() {
        schedulePresenter.logout()

        verify(user).delete()
    }

    @Test
    fun repositoryDeleteIsCalledOnLogout() {
        schedulePresenter.logout()

        verify(repository).delete()
    }

    @Test
    fun showLoginViewIsCalledOnLogout() {
        schedulePresenter.logout()

        verify(view).showLoginView()
    }

    private fun prepareMocksToReturnDatesFromRepositoryAndDatesProvider() {
        whenever(user.isSaved()).thenReturn(true)
        whenever(repository.getScheduleMinDate()).thenReturn(MIN_DATE)
        whenever(repository.getScheduleMaxDate()).thenReturn(MAX_DATE)
        whenever(datesProvider.getByInterval(MIN_DATE, MAX_DATE)).thenReturn(mutableListOf(DATE))
    }
}
