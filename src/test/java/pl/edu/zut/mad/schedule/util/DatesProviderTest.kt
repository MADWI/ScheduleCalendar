package pl.edu.zut.mad.schedule.util

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.Days
import org.joda.time.LocalDate
import org.junit.Test

class DatesProviderTest {

    private val startDate = LocalDate.parse("2016-01-01")
    private val endDate = LocalDate.parse("2017-01-01")
    private val datesProvider = DatesProvider()

    @Test
    fun datesLengthIsEqualToDaysBetweenInclusive() {
        val dates = datesProvider.getByInterval(startDate, endDate)

        val daysBetweenInclusive = Days.daysBetween(dates.first(), dates.last()).days + 1

        assertThat(daysBetweenInclusive).isEqualTo(dates.size)
    }

    @Test
    fun firstDayIsEqualToStartDate() {
        val dates = datesProvider.getByInterval(startDate, endDate)

        assertThat(dates.first()).isEqualTo(startDate)
    }

    @Test
    fun lastDayIsEqualToEndDate() {
        val dates = datesProvider.getByInterval(startDate, endDate)

        assertThat(dates.last()).isEqualTo(endDate)
    }
}
