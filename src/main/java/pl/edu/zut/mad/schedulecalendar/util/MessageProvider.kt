package pl.edu.zut.mad.schedulecalendar.util

import pl.edu.zut.mad.schedulecalendar.R
import retrofit2.HttpException


class MessageProvider {

    fun getIdByHttpError(error: Throwable): Int {
        if (error is HttpException) {
            when (error.code()) {
                500 -> return R.string.error_service_internal
                504 -> return R.string.error_service_database_update
            }
        }
        return R.string.error_service_unrecognized
    }
}
