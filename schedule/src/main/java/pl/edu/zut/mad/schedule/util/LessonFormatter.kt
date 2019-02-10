package pl.edu.zut.mad.schedule.util

import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal class LessonFormatter(private val lesson: Lesson) {

    fun getSubjectWithType() = "${lesson.subject} (${lesson.type})"

    fun getTeacherWithRoom() =
        with(lesson.teacher) { "$academicTitle $name $surname ${lesson.room}" }
}
