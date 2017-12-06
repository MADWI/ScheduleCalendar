package pl.edu.zut.mad.schedule.search

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.RxImmediateSchedulerRule
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.data.model.api.Day
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

@Suppress("IllegalIdentifier")
internal class SearchPresenterTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxImmediateSchedulerRule()

    val view: SearchMvp.View = mock()
    val service: ScheduleService = mock()
    val modelMapper: ModelMapper = mock()
    val messageProvider: MessageProviderSearch = mock()
    val networkConnection: NetworkConnection = mock()

    @InjectMocks
    lateinit var presenter: SearchPresenter

    @Test
    fun `search should call hide loading when connection is not available`() {
        whenever(networkConnection.isAvailable()).thenReturn(false)

        presenter.onSearch()

        verify(view).hideLoading()
    }

    @Test
    fun `search should call show error when connection is not available`() {
        whenever(networkConnection.isAvailable()).thenReturn(false)

        presenter.onSearch()

        verify(view).showError(R.string.error_no_internet)
    }

    @Test
    fun `search should call fetch schedule when connection is available`() {
        prepareServiceMockToReturnObservable(Observable.just(emptyList()))

        presenter.onSearch()

        verify(service).fetchScheduleByQueries(any())
    }

    @Test
    fun `search should call hide loading when service return data`() {
        prepareServiceMockToReturnObservable(Observable.just(emptyList()))

        presenter.onSearch()

        verify(view).hideLoading()
    }

    @Test
    fun `search should call set data when schedule service return data`() {
        prepareServiceMockToReturnObservable(Observable.just(emptyList()))

        presenter.onSearch()

        verify(view).setData(any())
    }

    @Test
    fun `search should call hide loading when schedule service return error`() {
        prepareServiceMockToReturnObservable(Observable.error(Throwable()))

        presenter.onSearch()

        verify(view).hideLoading()
    }

    @Test
    fun `search should call show error when schedule service return error`() {
        prepareServiceMockToReturnObservable(Observable.error(Throwable()))

        presenter.onSearch()

        verify(view).showError(any())
    }

    private fun prepareServiceMockToReturnObservable(observable: Observable<List<Day>>) {
        whenever(networkConnection.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByQueries(any())).thenReturn(observable)
    }
}
