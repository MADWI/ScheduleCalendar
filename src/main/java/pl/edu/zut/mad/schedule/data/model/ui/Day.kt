package pl.edu.zut.mad.schedule.data.model.ui

import org.joda.time.LocalDate

sealed class OptionalDay
data class EmptyDay(val date: LocalDate) : OptionalDay()

data class Day(val date: LocalDate, val lessons: List<Lesson>) : OptionalDay()
