package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmObject

// TODO model should be changed due to different layout for lesson view
open class Lesson(private var subject: String = "", private var courseType: String = "",
                  private var room: String = "", private var teacher: Teacher = Teacher(),
                  var timeRange: TimeRange? = TimeRange()) : RealmObject() {

    val subjectNameWithType: String
        get() = "$subject ($courseType)"

    val lecturerWithRoom: String
        get() = "${teacher.academicTitle} ${teacher.name} ${teacher.surname} $room"
}
