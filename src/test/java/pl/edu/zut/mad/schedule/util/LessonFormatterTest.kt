package pl.edu.zut.mad.schedule.util

import com.tngtech.java.junit.dataprovider.DataProviderRunner
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.zut.mad.schedule.MockData
import pl.edu.zut.mad.schedule.MockData.Companion.SUBJECT_WITH_TYPE
import pl.edu.zut.mad.schedule.MockData.Companion.TEACHER_WITH_ROOM
import pl.edu.zut.mad.schedule.data.model.ui.Day

@Suppress("IllegalIdentifier")
@RunWith(DataProviderRunner::class)
internal class LessonFormatterTest {

    lateinit var lessonFromatter: LessonFormatter

    @Before
    fun setUp() {
        val day = MockData.dayUi()[0][0] as Day
        val lesson = day.lessons[0]
        lessonFromatter = LessonFormatter(lesson)
    }

    @Test
    fun `get subject with type should return proper text`() {
        val subjectWithType = lessonFromatter.getSubjectWithType()

        assertThat(subjectWithType).isEqualTo(SUBJECT_WITH_TYPE)
    }

    @Test
    fun `get teacher with room should return proper text`() {
        val teacherWithRoom = lessonFromatter.getTeacherWithRoom()

        assertThat(teacherWithRoom).isEqualTo(TEACHER_WITH_ROOM)
    }
}
