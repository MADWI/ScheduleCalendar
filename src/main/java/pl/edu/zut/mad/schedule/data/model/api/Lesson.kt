package pl.edu.zut.mad.schedule.data.model.api

import io.realm.RealmObject

internal open class Lesson(var subject: String = "", var courseType: String = "",
    var room: String = "", var teacher: Teacher = Teacher(),
    var reservationStatus: String = "", var timeRange: TimeRange = TimeRange(),
    var facultyAbbreviation: String = "", var fieldOfStudy: String = "",
    var semester: Int = 0) : RealmObject()
