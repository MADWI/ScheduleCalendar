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
import pl.edu.zut.mad.schedule.data.model.ui.OptionalDay
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

@Suppress("IllegalIdentifier")
@RunWith(DataProviderRunner::class)
internal class SchedulePresenterTest {

    companion object {
        private val DATE = LocalDate.now()
        private val MIN_DATE = LocalDate.now()
        private val MAX_DATE = MIN_DATE.plusMonths(1)
    }

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    val user = mock<User>()
    val mapper = mock<ModelMapper>()
    val view = mock<ScheduleMvp.View>()
    val datesProvider = mock<DatesProvider>()
    val connection = mock<NetworkConnection>()
    val repository = mock<ScheduleRepository>()

    @InjectMocks
    private lateinit var schedulePresenter: SchedulePresenter

    @Test
    fun `show loading view should be called when view is created`() {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()

        schedulePresenter.onViewIsCreated()

        verify(view).showLoadingView()
    }

    @Test
    fun `get min date should be called when user is saved and view is created`() {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()

        schedulePresenter.onViewIsCreated()

        verify(repository).getScheduleMinDate()
    }

    @Test
    fun `get max date should be called when user is saved and view is created`() {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()

        schedulePresenter.onViewIsCreated()

        verify(repository).getScheduleMaxDate()
    }

    @Test
    fun `get by date should be called when user is saved and view is created`() {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()

        schedulePresenter.onViewIsCreated()

        verify(repository).getDayByDate(any())
    }

    @Test
    @UseDataProvider("dayUi", location = [(MockData::class)])
    fun `mapping to lessons events should be called when user is saved and view is created`(day: Day) {
        prepareMocksToReturnDatesFromRepositoryAndDatesProviderAndDay(day)

        schedulePresenter.onViewIsCreated()

        verify(mapper).toLessonsEvents(day)
    }

    @Test
    @UseDataProvider("dayUi", location = [(MockData::class)])
    fun `set data should be called when repository return data`(day: Day) {
        prepareMocksToReturnDatesFromRepositoryAndDatesProviderAndDay(day)

        schedulePresenter.onViewIsCreated()

        verify(view).setData(any())
    }

    @Test
    @UseDataProvider("emptyDay", location = [(MockData::class)])
    fun `set data events should be be called when repository return empty day`(emptyDay: EmptyDay) {
        prepareMocksToReturnDatesFromRepositoryAndDatesProviderAndDay(emptyDay)

        schedulePresenter.onViewIsCreated()

        verify(view).setData(any())
    }

    @Test
    @UseDataProvider("dayUi", location = [(MockData::class)])
    fun `hide loading should be be called when repository return data`(day: Day) {
        prepareMocksToReturnDatesFromRepositoryAndDatesProviderAndDay(day)

        schedulePresenter.onViewIsCreated()

        verify(view).hideLoadingView()
    }

    @Test
    fun `on date interval calculated is called on view is created`() {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()

        schedulePresenter.onViewIsCreated()

        verify(view).onDateIntervalCalculated(MIN_DATE, MAX_DATE)
    }

    @Test
    fun `show login view should be called when user is not saved`() {
        whenever(user.isSaved()).thenReturn(false)

        schedulePresenter.onViewIsCreated()

        verify(view).showLoginView()
    }

    @Test
    fun `refresh schedule should be called when connection is available`() {
        whenever(connection.isAvailable()).thenReturn(true)

        schedulePresenter.refresh()

        verify(view).refreshSchedule(any())
    }

    @Test
    fun `get album number should be called when connection is available`() {
        whenever(connection.isAvailable()).thenReturn(true)

        schedulePresenter.refresh()

        verify(user).getAlbumNumber()
    }

    @Test
    fun `show internet error should be called when connection is not available`() {
        whenever(connection.isAvailable()).thenReturn(false)

        schedulePresenter.refresh()

        verify(view).showInternetError()
    }

    @Test
    fun `user delete should be called on logout`() {
        schedulePresenter.logout()

        verify(user).delete()
    }

    @Test
    fun `repository delete should be called on logout`() {
        schedulePresenter.logout()

        verify(repository).delete()
    }

    @Test
    fun `show login view should be called on logout`() {
        schedulePresenter.logout()

        verify(view).showLoginView()
    }

    private fun prepareMocksToReturnDatesFromRepositoryAndDatesProvider() {
        whenever(user.isSaved()).thenReturn(true)
        whenever(repository.getScheduleMinDate()).thenReturn(Observable.just(MIN_DATE))
        whenever(repository.getScheduleMaxDate()).thenReturn(Observable.just(MAX_DATE))
        whenever(datesProvider.getByInterval(MIN_DATE, MAX_DATE)).thenReturn(mutableListOf(DATE))
    }

    private fun prepareMocksToReturnDatesFromRepositoryAndDatesProviderAndDay(day: OptionalDay) {
        prepareMocksToReturnDatesFromRepositoryAndDatesProvider()
        whenever(repository.getDayByDate(DATE)).thenReturn(Observable.just(day))
    }
}
