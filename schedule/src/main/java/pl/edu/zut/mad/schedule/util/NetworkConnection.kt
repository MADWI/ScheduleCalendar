package pl.edu.zut.mad.schedule.util

import android.content.Context
import android.net.ConnectivityManager

internal class NetworkConnection(private val context: Context) {

    fun isAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isAvailable == true
    }
}
