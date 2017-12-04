package pl.edu.zut.mad.schedule.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@Suppress("IllegalIdentifier")
class NetworkConnectionTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @InjectMocks
    private lateinit var networkConnection: NetworkConnection

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
    }

    @Test
    fun `is available is true when network info return true`() {
        val networkInfo = mock<NetworkInfo>()

        whenever(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isAvailable).thenReturn(true)

        assertThat(networkConnection.isAvailable()).isTrue()
    }

    @Test
    fun `is available is false when network info return false`() {
        val networkInfo = mock<NetworkInfo>()

        whenever(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isAvailable).thenReturn(false)

        assertThat(networkConnection.isAvailable()).isFalse()
    }

    @Test
    fun `is available is true when network info is null`() {
        whenever(connectivityManager.activeNetworkInfo).thenReturn(null)

        assertThat(networkConnection.isAvailable()).isFalse()
    }
}
