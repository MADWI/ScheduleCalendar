package pl.edu.zut.mad.schedulecalendar.data.model.ui

import org.joda.time.LocalDate


data class Day(val date: LocalDate, val lessons: List<Lesson>)
