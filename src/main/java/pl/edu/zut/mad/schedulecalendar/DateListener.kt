package pl.edu.zut.mad.schedulecalendar

import org.joda.time.LocalDate


interface DateListener {
    fun onDateChanged(date: LocalDate)
}
