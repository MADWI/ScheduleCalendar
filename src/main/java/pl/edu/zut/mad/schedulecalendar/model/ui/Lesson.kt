package pl.edu.zut.mad.schedulecalendar.model.ui

import pl.edu.zut.mad.schedulecalendar.model.TimeRange


data class Lesson(val teacherWithRoom: String, val subjectWithType: String,
                  val timeRange: TimeRange)
