package pl.edu.zut.mad.schedulecalendar.data.model.ui

import pl.edu.zut.mad.schedulecalendar.data.model.TimeRange


data class Lesson(val teacherWithRoom: String, val subjectWithType: String,
                  val timeRange: TimeRange)
