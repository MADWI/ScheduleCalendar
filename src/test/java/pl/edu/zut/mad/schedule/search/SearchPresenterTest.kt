package pl.edu.zut.mad.schedule.search

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
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

    companion object {
        val searchInputModel = SearchInput("", "", "", "", "", "", "", "", "", "", "")
    }

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxImmediateSchedulerRule()

    val searchInputModelSubject = PublishSubject.create<SearchInput>()
    val searchInputTextSubject = PublishSubject.create<Pair<String, String>>()

    val view: SearchMvp.View = mock {
        on { observeSearchInputModel() } doReturn searchInputModelSubject
        on { observeSearchInputText() } doReturn searchInputTextSubject
    }
    val service: ScheduleService = mock()
    val modelMapper: ModelMapper = mock()
    val messageProvider: MessageProviderSearch = mock()
    val networkConnection: NetworkConnection = mock()

    @InjectMocks
    lateinit var presenter: SearchPresenter

    @Test
    fun `publish search model should call hide loading when connection is not available`() {
        whenever(networkConnection.isAvailable()).thenReturn(false)

        searchInputModelSubject.onNext(searchInputModel)

        verify(view).hideLoading()
    }

    @Test
    fun `publish search model should call show error when connection is not available`() {
        whenever(networkConnection.isAvailable()).thenReturn(false)

        searchInputModelSubject.onNext(searchInputModel)

        verify(view).showError(R.string.error_no_internet)
    }

    @Test
    fun `publish search model should call fetch schedule when connection is available`() {
        prepareServiceMockToReturnScheduleObservable(Observable.just(emptyList()))

        searchInputModelSubject.onNext(searchInputModel)

        verify(service).fetchScheduleByQueries(any())
    }

    @Test
    fun `publish search model should call hide loading when service return data`() {
        prepareServiceMockToReturnScheduleObservable(Observable.just(emptyList()))

        searchInputModelSubject.onNext(searchInputModel)

        verify(view).hideLoading()
    }

    @Test
    fun `publish search model should call set data when schedule service return data`() {
        prepareServiceMockToReturnScheduleObservable(Observable.just(emptyList()))

        searchInputModelSubject.onNext(searchInputModel)

        verify(view).setData(any())
    }

    @Test
    fun `publish search model should call hide loading when schedule service return error`() {
        prepareServiceMockToReturnScheduleObservable(Observable.error(Throwable()))

        searchInputModelSubject.onNext(searchInputModel)

        verify(view).hideLoading()
    }

    @Test
    fun `publish search model should call show error when schedule service return error`() {
        prepareServiceMockToReturnScheduleObservable(Observable.error(Throwable()))

        searchInputModelSubject.onNext(searchInputModel)

        verify(view).showError(any())
    }

    @Test
    fun `publish search text should call show suggestion when schedule service return data`() {
        prepareServiceMockToReturnSuggestionsObservable(Observable.just(emptyList()))
        val filterField = "name"

        searchInputTextSubject.onNext(Pair(filterField, ""))

        verify(view).showSuggestions(any(), eq(filterField))
    }

    private fun prepareServiceMockToReturnScheduleObservable(observable: Observable<List<Day>>) {
        prepareNetworkConnectionMockToReturnTrueForIsAvailable()
        whenever(service.fetchScheduleByQueries(any())).thenReturn(observable)
    }

    private fun prepareServiceMockToReturnSuggestionsObservable(observable: Observable<List<String>>) {
        prepareNetworkConnectionMockToReturnTrueForIsAvailable()
        whenever(service.fetchSuggestions(any(), any())).thenReturn(observable)
    }

    private fun prepareNetworkConnectionMockToReturnTrueForIsAvailable() =
        whenever(networkConnection.isAvailable()).thenReturn(true)
}
