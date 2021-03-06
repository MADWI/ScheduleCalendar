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
import pl.edu.zut.mad.schedule.util.NetworkConnection

@Suppress("IllegalIdentifier")
@RunWith(DataProviderRunner::class)
internal class LoginPresenterTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    val user = mock<User>()
    val view = mock<LoginMvp.View>()
    val service = mock<ScheduleService>()
    val network = mock<NetworkConnection>()
    val repository = mock<ScheduleRepository>()
    val messageProvider = mock<MessageProviderLogin>()

    @InjectMocks
    private lateinit var loginPresenter: LoginPresenter

    companion object {
        private const val ALBUM_NUMBER = "32190"
        private const val EMPTY_ALBUM_NUMBER = ""
    }

    @Test
    fun `show error should be called when album number is empty`() {
        whenever(view.getAlbumNumberText()).thenReturn(EMPTY_ALBUM_NUMBER)

        loginPresenter.onDownloadScheduleClick()

        verify(view).showError(R.string.error_field_cannot_be_empty)
    }

    @Test
    fun `show error should be called when network is not available`() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(false)

        loginPresenter.onDownloadScheduleClick()

        verify(view).showError(R.string.error_no_internet)
    }

    @Test
    fun `show loading should be called when schedule service return data`() {
        prepareMocksToReturnDayListForSchedule(emptyList())

        loginPresenter.onDownloadScheduleClick()

        verify(view).showLoading()
    }

    @Test
    fun `fetch schedule should be called on presenter download click`() {
        prepareMocksToReturnDayListForSchedule(emptyList())

        loginPresenter.onDownloadScheduleClick()

        verify(service).fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt())
    }

    @Test
    fun `repository save should be called on presenter download click`() {
        prepareMocksToReturnDayListForSchedule(emptyList())

        loginPresenter.onDownloadScheduleClick()

        verify(repository).save(any())
    }

    @Test
    @UseDataProvider("dayApiList", location = [(MockData::class)])
    fun `hide loading should be called when schedule service return data`(days: List<Day>) {
        prepareMocksToReturnDayListForSchedule(days)

        loginPresenter.onDownloadScheduleClick()

        verify(view).hideLoading()
    }

    @Test
    @UseDataProvider("dayApiList", location = [(MockData::class)])
    fun `on data saved should be called when schedule service return data`(days: List<Day>) {
        prepareMocksToReturnDayListForSchedule(days)

        loginPresenter.onDownloadScheduleClick()

        verify(view).onDataSaved()
    }

    @Test
    @UseDataProvider("dayApiList", location = [(MockData::class)])
    fun `user save should be called when schedule service return data`(days: List<Day>) {
        prepareMocksToReturnDayListForSchedule(days)

        loginPresenter.onDownloadScheduleClick()

        verify(user).save(ALBUM_NUMBER.toInt())
    }

    @Test
    fun `hide loading should be called when schedule service return error`() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.error(Throwable()))

        loginPresenter.onDownloadScheduleClick()

        verify(view).hideLoading()
    }

    @Test
    fun `show error should be called when schedule service return error`() {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.error(Throwable()))

        loginPresenter.onDownloadScheduleClick()

        verify(view).showError(any())
    }

    private fun prepareMocksToReturnDayListForSchedule(days: List<Day>) {
        whenever(view.getAlbumNumberText()).thenReturn(ALBUM_NUMBER)
        whenever(network.isAvailable()).thenReturn(true)
        whenever(service.fetchScheduleByAlbumNumber(ALBUM_NUMBER.toInt()))
            .thenReturn(Observable.just(days))
        whenever(repository.save(days)).thenReturn(Completable.complete())
    }
}
