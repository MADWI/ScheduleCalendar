package pl.edu.zut.mad.schedule.util

import pl.edu.zut.mad.schedule.R
import retrofit2.HttpException


class MessageProvider {

    fun getIdByHttpError(error: Throwable): Int {
        if (error is HttpException) {
            when (error.code()) {
                404 -> return R.string.error_album_number_not_found
                500 -> return R.string.error_service_internal
                504 -> return R.string.error_service_database_update
            }
        }
        return R.string.error_service_unrecognized
    }
}
