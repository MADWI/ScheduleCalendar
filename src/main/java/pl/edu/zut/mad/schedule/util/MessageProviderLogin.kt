package pl.edu.zut.mad.schedule.util

import pl.edu.zut.mad.schedule.R

internal class MessageProviderLogin : MessageProvider() {

    override fun getNotFoundMessageId() = R.string.error_album_number_not_found
}
