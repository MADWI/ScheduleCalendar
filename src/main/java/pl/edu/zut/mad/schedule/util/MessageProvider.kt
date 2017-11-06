package pl.edu.zut.mad.schedule.util

import pl.edu.zut.mad.schedule.R
import retrofit2.HttpException
import java.net.HttpURLConnection

internal class MessageProvider {

    fun getIdByHttpError(error: Throwable): Int {
        if (error is HttpException) {
            when (error.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> return R.string.error_album_number_not_found
                HttpURLConnection.HTTP_UNAVAILABLE -> return R.string.error_service_database_update
                HttpURLConnection.HTTP_INTERNAL_ERROR -> return R.string.error_service_internal
            }
        }
        return R.string.error_service_unrecognized
    }
}
