package pl.edu.zut.mad.schedule.data.model.ui

import org.joda.time.LocalDate

internal sealed class OptionalDay
internal data class EmptyDay(val date: LocalDate) : OptionalDay()

internal data class Day(val date: LocalDate, val lessons: List<Lesson>) : OptionalDay()
