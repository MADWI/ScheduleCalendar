package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmObject
import java.util.*

// TODO model should be changed due to different layout for lesson view
open class Lesson(private var name: String = "", private var type: String = "",
                  private var room: String = "", private var teacher: String = "",
                  private var timeRange: TimeRange = TimeRange()) : RealmObject() {

    companion object {
        private const val TIME_FORMAT = "%02d:%02d"
    }

    val subjectNameWithType: String
        get() = "$name ($type)"

    val lecturerWithRoom: String
        get() = "$teacher $room"

    val startTime: String
        get() = getFormattedTime(timeRange.fromHour, timeRange.fromMinute)

    val endTime: String
        get() = getFormattedTime(timeRange.toHour, timeRange.toMinute)

    private fun getFormattedTime(hour: Int, minute: Int) =
            String.format(Locale.getDefault(), TIME_FORMAT, hour, minute)
}
