package pl.edu.zut.mad.schedule.util

import org.joda.time.Days
import org.joda.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DatesProviderTest {

    private val startDate = LocalDate.parse("2016-01-01")
    private val endDate = LocalDate.parse("2017-01-01")
    private val datesProvider = DatesProvider()

    @Test
    fun datesAmountIsProper() {
        val dates = datesProvider.getByInterval(startDate, endDate)

        val daysBetweenInclusive = Days.daysBetween(dates.first(), dates.last()).days + 1

        assertEquals(daysBetweenInclusive, dates.size)
    }

    @Test
    fun firstDayIsEqualsStartDate() {
        val dates = datesProvider.getByInterval(startDate, endDate)

        assertEquals(dates.first(), startDate)
    }

    @Test
    fun lastDayIsEqualsStartDate() {
        val dates = datesProvider.getByInterval(startDate, endDate)

        assertEquals(dates.last(), endDate)
    }
}
