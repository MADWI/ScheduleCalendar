package pl.edu.zut.mad.schedule.util

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import pl.edu.zut.mad.schedule.R

@Suppress("IllegalIdentifier")
internal class LessonIndexerTest {

    companion object {
        private const val LECTURE = "wykład"
        private const val AUDITORIUM = "audytoryjne"
        private const val LABORATORY = "laboratorium"
        private const val PROJECT = "projekt"
        private const val LANGUAGE_COURSE = "lektorat"
        private const val UNRECOGNIZED = "unrecognized"
    }

    lateinit var lessonIndexer: LessonIndexer

    @Before
    fun setUp() {
        val resources = mock<Resources>()
        val courseTypes = arrayOf(LECTURE, AUDITORIUM, LABORATORY, PROJECT, LANGUAGE_COURSE)
        whenever(resources.getStringArray(R.array.course_type_entries)).thenReturn(courseTypes)
        lessonIndexer = LessonIndexer(resources)
    }

    @Test
    fun `course type index should be 0 for others`() {
        val courseTypeIndex = lessonIndexer.getCourseTypeIndex(UNRECOGNIZED)

        assertThat(courseTypeIndex).isEqualTo(0)
    }

    @Test
    fun `course type index should be 1 for lecture`() {
        val courseTypeIndex = lessonIndexer.getCourseTypeIndex(LECTURE)

        assertThat(courseTypeIndex).isEqualTo(1)
    }

    @Test
    fun `course type index should be 2 for auditorium`() {
        val courseTypeIndex = lessonIndexer.getCourseTypeIndex(AUDITORIUM)

        assertThat(courseTypeIndex).isEqualTo(2)
    }

    @Test
    fun `course type index should be 3 for laboratory`() {
        val courseTypeIndex = lessonIndexer.getCourseTypeIndex(LABORATORY)

        assertThat(courseTypeIndex).isEqualTo(3)
    }

    @Test
    fun `course type index should be 4 for project`() {
        val courseTypeIndex = lessonIndexer.getCourseTypeIndex(PROJECT)

        assertThat(courseTypeIndex).isEqualTo(4)
    }

    @Test
    fun `course type index should be 5 for language course`() {
        val courseTypeIndex = lessonIndexer.getCourseTypeIndex(LANGUAGE_COURSE)

        assertThat(courseTypeIndex).isEqualTo(5)
    }
}
