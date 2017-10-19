package pl.edu.zut.mad.schedulecalendar.util

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtils(private val context: Context) {

    fun isAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isAvailable == true
    }
}
