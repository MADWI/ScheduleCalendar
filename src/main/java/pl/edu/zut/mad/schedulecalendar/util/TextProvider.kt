package pl.edu.zut.mad.schedulecalendar.util

import pl.edu.zut.mad.schedulecalendar.R
import retrofit2.HttpException


class TextProvider {

    fun getErrorMessageIdRes(error: Throwable): Int { // TODO: move to presenter or better place
        if (error is HttpException) {
            when (error.code()) {
                500 -> return R.string.error_service_internal
                504 -> return R.string.error_service_database_update
            }
        }
        return R.string.error_service_unrecognized
    }
}
