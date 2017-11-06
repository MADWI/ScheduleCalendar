package pl.edu.zut.mad.schedule.util

import org.joda.time.LocalDate

internal class DatesProvider {

    fun getByInterval(start: LocalDate, end: LocalDate): MutableList<LocalDate> {
        var nextDay = start
        val dates: MutableList<LocalDate> = ArrayList()
        while (!nextDay.isEqual(end)) {
            dates.add(nextDay)
            nextDay = nextDay.plusDays(1)
        }
        return dates
    }
}
