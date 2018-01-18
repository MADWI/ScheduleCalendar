package pl.edu.zut.mad.schedule

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

internal class ScheduleDate {

    companion object {
        const val API_PATTERN = "dd-MM-yyyy"
        const val TIME_SEARCH_LESSON_PATTERN = "%s-%s"
        @JvmStatic val UI_FORMATTER: DateTimeFormatter =
            DateTimeFormat.forPattern("dd-MM-yyyy")
        @JvmStatic val UI_LESSON_HEADER_FORMATTER: DateTimeFormatter =
            DateTimeFormat.forPattern("EEEE, d MMMM y")
    }
}
