package pl.edu.zut.mad.schedule.login

import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.edu.zut.mad.schedule.R
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

@Suppress("IllegalIdentifier")
class MessageProviderLoginTest {

    private val messageProvider = MessageProviderLogin()

    @Test
    fun `provider return res id album number not found when error is 404`() {
        val httpError = getHttpErrorWithCode(404)

        val messageId = messageProvider.getResIdByError(httpError)

        assertThat(R.string.error_album_number_not_found).isEqualTo(messageId)
    }

    @Test
    fun `provider return res id database update when error is 503`() {
        val httpError = getHttpErrorWithCode(503)

        val messageId = messageProvider.getResIdByError(httpError)

        assertThat(R.string.error_service_database_update).isEqualTo(messageId)
    }

    @Test
    fun `provider return res id internal when error is 500`() {
        val httpError = getHttpErrorWithCode(500)

        val messageId = messageProvider.getResIdByError(httpError)

        assertThat(R.string.error_service_internal).isEqualTo(messageId)
    }

    @Test
    fun `provider return res id unrecognized in other cases`() {
        val messageId = messageProvider.getResIdByError(Exception())

        assertThat(R.string.error_service_unrecognized).isEqualTo(messageId)
    }

    private fun getHttpErrorWithCode(code: Int): HttpException {
        val responseBody = ResponseBody.create(null, "")
        val response: Response<String> = Response.error(code, responseBody)
        return HttpException(response)
    }
}
