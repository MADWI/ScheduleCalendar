package pl.edu.zut.mad.schedule.search

import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.edu.zut.mad.schedule.R
import retrofit2.HttpException
import retrofit2.Response

class MessageProviderSearchTest {

    private val messageProvider = MessageProviderSearch()

    @Test
    fun providerReturnIdResEmptyScheduleAlbumNumberNotFoundWhenErrorIs404() {
        val httpError = getHttpErrorWithCode(404)

        val messageId = messageProvider.getResIdByError(httpError)

        assertThat(R.string.error_empty_schedule).isEqualTo(messageId)
    }

    private fun getHttpErrorWithCode(code: Int): HttpException {
        val responseBody = ResponseBody.create(null, "")
        val response: Response<String> = Response.error(code, responseBody)
        return HttpException(response)
    }
}
