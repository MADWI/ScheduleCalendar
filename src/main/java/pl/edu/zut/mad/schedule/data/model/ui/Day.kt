package pl.edu.zut.mad.schedule.data.model.ui

import org.joda.time.LocalDate


data class Day(val date: LocalDate, val lessons: List<Lesson>) // TODO: should by collapsed with ui.Lesson
