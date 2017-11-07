package pl.edu.zut.mad.schedule.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class NetworkConnectionTest {

    private val context = mock<Context>()
    private val connectivityManager = mock<ConnectivityManager>()
    private lateinit var networkConnection: NetworkConnection

    @Before
    fun setUp() {
        networkConnection = NetworkConnection(context)
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
    }

    @Test
    fun isAvailableReturnsTrue() {
        val networkInfo = mock<NetworkInfo>()

        whenever(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isAvailable).thenReturn(true)

        assertThat(networkConnection.isAvailable()).isTrue()
    }

    @Test
    fun isAvailableReturnsFalse() {
        val networkInfo = mock<NetworkInfo>()

        whenever(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isAvailable).thenReturn(false)

        assertThat(networkConnection.isAvailable()).isFalse()
    }

    @Test
    fun isAvailableReturnsFalseWhenNetworkInfoIsNull() {
        whenever(connectivityManager.activeNetworkInfo).thenReturn(null)

        assertThat(networkConnection.isAvailable()).isFalse()
    }
}
