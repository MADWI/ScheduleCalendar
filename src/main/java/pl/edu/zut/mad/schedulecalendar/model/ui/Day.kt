package pl.edu.zut.mad.schedulecalendar.model.ui

import org.joda.time.LocalDate


data class Day(val date: LocalDate, val lessons: List<Lesson>)
