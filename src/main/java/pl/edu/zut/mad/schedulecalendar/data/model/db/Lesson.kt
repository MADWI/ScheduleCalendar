package pl.edu.zut.mad.schedulecalendar.data.model.db

import io.realm.RealmObject


open class Lesson(var subject: String = "", var courseType: String = "",
                  var room: String = "", var teacher: Teacher? = null,
                  var timeRange: TimeRange? = null) : RealmObject()
