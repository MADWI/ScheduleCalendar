package pl.edu.zut.mad.schedule.data.model.ui

import pl.edu.zut.mad.schedule.data.model.api.TimeRange


data class Lesson(val teacherWithRoom: String, val subjectWithType: String,
                  val isCancelled: Boolean, val timeRange: TimeRange)
