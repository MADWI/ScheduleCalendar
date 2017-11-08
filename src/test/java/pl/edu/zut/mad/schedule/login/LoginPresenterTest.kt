package pl.edu.zut.mad.schedule.login

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import pl.edu.zut.mad.schedule.MockData
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.RxImmediateSchedulerRule
import pl.edu.zut.mad.schedule.User
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.data.model.api.Day
import pl.edu.zut.mad.schedule.util.MessageProvider
import pl.edu.zut.mad.schedule.util.NetworkConnection

@RunWith(DataProviderRunner::class)
internal class LoginPresenterTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    private val user = mock<User>()
    private val view = mock<LoginMvp.View>()
    private val service = mock<ScheduleService>()
    private val network = mock<NetworkConnection>()
    private val repository = mock<ScheduleRepository>()
    private val messageProvider = mock<MessageProvider>()

    @InjectMocks
    private lateinit var loginPresenter: LoginPresenter

    companion object {
        private const val ALBUM_NUMBER = "32190"
        private const val EMPTY_ALBUM_NUMBER = ""
    }

    @Test
    fun showErrorIsCalledWhenAlbumNumberIsEmpty() {
        whenever(view.getAlbumNumberText()).thenReturn(EMPTY_ALBUM_NUMBER)

        loginPresenter.onDownloadScheduleClick()

        verify(view).showError(R.string.error_field_cannot_be_empty)
    }

    @Test
    fun showErrorIsCalledWhenNetworkIsNotAvailable() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(false)

        loginPresenter.onDownloadScheduleClick()

        verify(view).showError(R.string.error_no_internet)
    }

    @Test
    fun showLoadingIsCalledWhenServiceFetchScheduleReturnsDays() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.just(emptyList()))

        loginPresenter.onDownloadScheduleClick()

        verify(view).showLoading()
    }

    @Test
    fun fetchScheduleIsCalledWhenServiceFetchScheduleReturnsDays() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.just(emptyList()))

        loginPresenter.onDownloadScheduleClick()

        verify(service).fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt())
    }

    @Test
    fun repositorySaveIsCalledWhenServiceFetchScheduleReturnsDays() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.just(emptyList()))

        loginPresenter.onDownloadScheduleClick()

        verify(repository).save(any())
    }

    @Test
    @UseDataProvider("dayApiList", location = arrayOf(MockData::class))
    fun hideLoadingIsCalledWhenServiceFetchScheduleReturnsDays(days: List<Day>) {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.just(days))

        loginPresenter.onDownloadScheduleClick()

        verify(view).hideLoading()
    }

    @Test
    @UseDataProvider("dayApiList", location = arrayOf(MockData::class))
    fun onDataSavedIsCalledWhenServiceFetchScheduleReturnsDays(days: List<Day>) {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.just(days))
        whenever(repository.save(days)).thenReturn(Completable.complete())

        loginPresenter.onDownloadScheduleClick()

        verify(view).onDataSaved()
    }

    @Test
    @UseDataProvider("dayApiList", location = arrayOf(MockData::class))
    fun userSaveIsCalledWhenServiceFetchScheduleReturnsDays(days: List<Day>) {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.just(days))
        whenever(repository.save(days)).thenReturn(Completable.complete())

        loginPresenter.onDownloadScheduleClick()

        verify(user).save(ALBUM_NUMBER.toInt())
    }

    @Test
    fun hideLoadingIsCalledWhenFetchScheduleReturnsError() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.error(Throwable()))

        loginPresenter.onDownloadScheduleClick()

        verify(view).hideLoading()
    }

    @Test
    fun showErrorIsCalledWhenFetchScheduleReturnsError() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.error(Throwable()))

        loginPresenter.onDownloadScheduleClick()

        verify(view).showError(any())
    }
}
