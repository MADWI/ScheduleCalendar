package pl.edu.zut.mad.schedulecalendar.module

import android.content.Context
import pl.edu.zut.mad.schedulecalendar.R
import retrofit2.HttpException


class TextProvider(private val context: Context) {

    fun getErrorMessageByThrowable(error: Throwable): String {
        val messageId = getErrorMessageIdRes(error)
        return context.getString(messageId)
    }

    private fun getErrorMessageIdRes(error: Throwable): Int {
        if (error is HttpException) {
            when (error.code()) {
                500 -> return R.string.error_service_internal
                504 -> return R.string.error_service_database_update
            }
        }
        return R.string.error_service_unrecognized
    }
}
