package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmObject

// TODO model should be changed due to different layout for lesson view
open class Lesson(private var name: String = "", private var type: String = "",
                  private var room: String = "", private var teacher: String = "",
                  var timeRange: TimeRange? = TimeRange()) : RealmObject() {

    val subjectNameWithType: String
        get() = "$name ($type)"

    val lecturerWithRoom: String
        get() = "$teacher $room"
}
