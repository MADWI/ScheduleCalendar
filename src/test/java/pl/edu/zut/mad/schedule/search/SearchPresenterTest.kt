package pl.edu.zut.mad.schedule.search

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
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
    fun onSearchShouldCallViewHideLoadingWhenConnectionIsNotAvailable() {
        whenever(networkConnection.isAvailable()).thenReturn(false)

        presenter.onSearch()

        verify(view).hideLoading()
    }

    @Test
    fun onSearchShouldCallViewShowErrorWhenConnectionIsNotAvailable() {
        whenever(networkConnection.isAvailable()).thenReturn(false)

        presenter.onSearch()

        verify(view).showError(R.string.error_no_internet)
    }

    @Test
    fun onSearchShouldCallServiceFetchScheduleWhenConnectionIsAvailable() {
        prepareServiceMockToReturnObservable(Observable.just(emptyList()))

        presenter.onSearch()

        verify(service).fetchScheduleByQueries(any(), any(), any(), any(), any(), any(), eq(null), any(), any(), any())
    }

    @Test
    fun onSearchShouldCallViewHideLoadingWhenServiceReturnData() {
        prepareServiceMockToReturnObservable(Observable.just(emptyList()))

        presenter.onSearch()

        verify(view).hideLoading()
    }

    @Test
    fun onSearchShouldCallViewOnScheduleDownloadedWhenServiceReturnData() {
        prepareServiceMockToReturnObservable(Observable.just(emptyList()))

        presenter.onSearch()

        verify(view).setData(any())
    }

    @Test
    fun onSearchShouldCallViewHideLoadingWhenServiceReturnError() {
        prepareServiceMockToReturnObservable(Observable.error(Throwable()))

        presenter.onSearch()

        verify(view).hideLoading()
    }

    @Test
    fun onSearchShouldCallViewShowErrorWhenServiceReturnError() {
        prepareServiceMockToReturnObservable(Observable.error(Throwable()))

        presenter.onSearch()

        verify(view).showError(any())
    }

    private fun prepareServiceMockToReturnObservable(observable: Observable<List<Day>>) {
        prepareViewMocksToReturnQuery()
        whenever(networkConnection.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByQueries(any(), any(), any(), any(), any(), any(), eq(null),
            any(), any(), any())).thenReturn(observable)
    }

    private fun prepareViewMocksToReturnQuery() {
        whenever(view.getTeacherName()).thenReturn("")
        whenever(view.getTeacherSurname()).thenReturn("")
        whenever(view.getFacultyAbbreviation()).thenReturn("")
        whenever(view.getSubject()).thenReturn("")
        whenever(view.getFieldOfStudy()).thenReturn("")
        whenever(view.getCourseType()).thenReturn("")
        whenever(view.getSemester()).thenReturn(null)
        whenever(view.getForm()).thenReturn("")
        whenever(view.getDateFrom()).thenReturn("")
        whenever(view.getDateTo()).thenReturn("")
    }
}