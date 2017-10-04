package pl.edu.zut.mad.schedule.util

import junit.framework.Assert.assertEquals
import okhttp3.ResponseBody
import org.junit.Test
import pl.edu.zut.mad.schedule.R
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class MessageProviderTest {

    private val messageProvider = MessageProvider()

    private fun getHttpErrorWithCode(code: Int): HttpException {
        val responseBody = ResponseBody.create(null, "")
        val response: Response<String> = Response.error(code, responseBody)
        return HttpException(response)
    }

    @Test
    fun properIdFor404() {
        val httpError = getHttpErrorWithCode(404)

        val messageId = messageProvider.getResIdByError(httpError)

        assertEquals(R.string.error_album_number_not_found, messageId)
    }

    @Test
    fun properIdFor503() {
        val httpError = getHttpErrorWithCode(503)

        val messageId = messageProvider.getResIdByError(httpError)

        assertEquals(R.string.error_service_database_update, messageId)
    }

    @Test
    fun properIdFor500() {
        val httpError = getHttpErrorWithCode(500)

        val messageId = messageProvider.getResIdByError(httpError)

        assertEquals(R.string.error_service_internal, messageId)
    }

    @Test
    fun properIdForOther() {
        val messageId = messageProvider.getResIdByError(Exception())

        assertEquals(R.string.error_service_unrecognized, messageId)
    }
}
