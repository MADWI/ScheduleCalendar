package pl.edu.zut.mad.schedule.data.model.ui

import org.joda.time.LocalDate

internal data class Lesson(val teacherWithRoom: String, val subjectWithType: String,
    val isCancelled: Boolean, val timeRange: TimeRange, val date: LocalDate)
