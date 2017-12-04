package pl.edu.zut.mad.schedule.util

import android.support.annotation.IdRes
import pl.edu.zut.mad.schedule.R
import retrofit2.HttpException
import java.net.HttpURLConnection

internal abstract class MessageProvider {

    fun getResIdByError(error: Throwable): Int {
        if (error is HttpException) {
            when (error.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> return getNotFoundMessageId()
                HttpURLConnection.HTTP_UNAVAILABLE -> return R.string.error_service_database_update
                HttpURLConnection.HTTP_INTERNAL_ERROR -> return R.string.error_service_internal
            }
        }
        return R.string.error_service_unrecognized
    }

    @IdRes abstract fun getNotFoundMessageId(): Int
}
