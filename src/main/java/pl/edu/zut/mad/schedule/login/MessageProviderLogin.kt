package pl.edu.zut.mad.schedule.login

import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.util.MessageProvider

internal class MessageProviderLogin : MessageProvider() {

    override fun getNotFoundMessageId() = R.string.error_album_number_not_found
}
