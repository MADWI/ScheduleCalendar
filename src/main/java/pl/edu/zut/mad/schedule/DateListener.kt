package pl.edu.zut.mad.schedule

import org.joda.time.LocalDate

interface DateListener {
    fun onDateChanged(date: LocalDate)
}
