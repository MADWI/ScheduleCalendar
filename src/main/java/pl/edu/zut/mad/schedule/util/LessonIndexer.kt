package pl.edu.zut.mad.schedule.util

import android.content.res.Resources
import pl.edu.zut.mad.schedule.R

internal class LessonIndexer(resources: Resources) {

    companion object {
        private const val COURSE_TYPE_INDEX_OFFSET = 1
    }

    private val types: Array<String> = resources.getStringArray(R.array.course_type_entries)

    fun getCourseTypeIndex(courseType: String): Int {
        val typeIndex = (0 until types.size).firstOrNull { types[it] == courseType }
        return if (typeIndex != null) typeIndex + COURSE_TYPE_INDEX_OFFSET else 0
    }
}
