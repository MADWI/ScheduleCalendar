package pl.edu.zut.mad.schedulecalendar

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtils {

    fun isAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isAvailable == true
    }
}
