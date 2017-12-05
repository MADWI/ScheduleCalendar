package pl.edu.zut.mad.schedule.util

import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal class LessonFormatter(private val lesson: Lesson) {

    fun getSubjectWithType() = "${lesson.subject} (${lesson.type})" //TODO tests

    fun getTeacherWithRoom() = lesson.teacherWithRoom
//    "${teacher?.academicTitle} ${teacher?.name} ${teacher?.surname} $room"
}
