package pl.edu.zut.mad.schedule.search

import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.util.MessageProvider

internal class MessageProviderSearch : MessageProvider() {

    override fun getNotFoundMessageId() = R.string.error_empty_schedule
}
