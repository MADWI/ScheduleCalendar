package pl.edu.zut.mad.schedulecalendar.model.db

import io.realm.RealmObject
import pl.edu.zut.mad.schedulecalendar.model.Teacher
import pl.edu.zut.mad.schedulecalendar.model.TimeRange


open class Lesson(var subject: String = "", var courseType: String = "",
                  var room: String = "", var teacher: Teacher? = null,
                  var timeRange: TimeRange? = null) : RealmObject()
