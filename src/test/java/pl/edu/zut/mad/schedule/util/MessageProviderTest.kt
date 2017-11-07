package pl.edu.zut.mad.schedule.util

import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import pl.edu.zut.mad.schedule.R
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class MessageProviderTest {

    private lateinit var messageProvider: MessageProvider

    @Before
    fun setUp() {
        messageProvider = MessageProvider()
    }

    @Test
    fun properIdFor404() {
        val httpError = getHttpErrorWithCode(404)

        val messageId = messageProvider.getResIdByError(httpError)

        assertThat(R.string.error_album_number_not_found).isEqualTo(messageId)
    }

    @Test
    fun properIdFor503() {
        val httpError = getHttpErrorWithCode(503)

        val messageId = messageProvider.getResIdByError(httpError)

        assertThat(R.string.error_service_database_update).isEqualTo(messageId)
    }

    @Test
    fun properIdFor500() {
        val httpError = getHttpErrorWithCode(500)

        val messageId = messageProvider.getResIdByError(httpError)

        assertThat(R.string.error_service_internal).isEqualTo(messageId)
    }

    @Test
    fun properIdForOther() {
        val messageId = messageProvider.getResIdByError(Exception())

        assertThat(R.string.error_service_unrecognized).isEqualTo(messageId)
    }

    private fun getHttpErrorWithCode(code: Int): HttpException {
        val responseBody = ResponseBody.create(null, "")
        val response: Response<String> = Response.error(code, responseBody)
        return HttpException(response)
    }
}
