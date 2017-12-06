package pl.edu.zut.mad.schedule.util

import android.content.res.Resources
import pl.edu.zut.mad.schedule.R

class LessonIndexer(resources: Resources) {

    private val types: Array<String> = resources.getStringArray(R.array.course_type_entries)

    fun getCourseTypeIndex(courseType: String): Int {
        val typeIndex = (0 until types.size).firstOrNull { types[it] == courseType }
        return if (typeIndex != null) typeIndex + 1 else 0
    }
}
