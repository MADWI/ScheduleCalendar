package pl.edu.zut.mad.schedule.util

import org.joda.time.LocalDate
import org.junit.Test

class DatesProviderTest {

    @Test
    fun getByInterval() {
        val datesProvider = DatesProvider()
        val startDate = LocalDate.parse("2016-01-01")
        val endDate = LocalDate.parse("2017-01-01")
        val dates = datesProvider.getByInterval(startDate, endDate)
    }
}
