package pl.edu.zut.mad.schedule.util

import org.joda.time.LocalDate


class DatesProvider {

    fun getByInterval(start: LocalDate, end: LocalDate): MutableList<LocalDate> {
        var nextDay = start
        val dateDates: MutableList<LocalDate> = ArrayList()
        while (!nextDay.isEqual(end)) {
            dateDates.add(nextDay)
            nextDay = nextDay.plusDays(1)
        }
        return dateDates
    }
}
