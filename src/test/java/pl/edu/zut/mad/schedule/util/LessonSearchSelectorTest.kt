package pl.edu.zut.mad.schedule.util

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import pl.edu.zut.mad.schedule.R

@Suppress("IllegalIdentifier")
class LessonSearchSelectorTest {

    companion object {
        private const val LECTURE = "wykład"
        private const val AUDITORIUM = "audytoryjne"
        private const val LABORATORY = "laboratorium"
        private const val PROJECT = "projekt"
        private const val LANGUAGE_COURSE = "lektorat"
        private const val UNRECOGNIZED = "unrecognized"
    }

    val resources = mock<Resources>()
    lateinit var lessonSearchSelector: LessonSearchSelector

    @Before
    fun setUp() {
        val courseTypes = arrayOf(LECTURE, AUDITORIUM, LABORATORY, PROJECT, LANGUAGE_COURSE)
        whenever(resources.getStringArray(R.array.course_type_entries)).thenReturn(courseTypes)
        lessonSearchSelector = LessonSearchSelector(resources)
    }

    @Test
    fun `type selection should be 0 for others`() {
        val typeSelection = lessonSearchSelector.getCourseTypeSelection(UNRECOGNIZED)

        assertThat(typeSelection).isEqualTo(0)
    }

    @Test
    fun `type selection should be 1 for lecture`() {
        val typeSelection = lessonSearchSelector.getCourseTypeSelection(LECTURE)

        assertThat(typeSelection).isEqualTo(1)
    }

    @Test
    fun `type selection should be 2 for auditorium`() {
        val typeSelection = lessonSearchSelector.getCourseTypeSelection(AUDITORIUM)

        assertThat(typeSelection).isEqualTo(2)
    }

    @Test
    fun `type selection should be 3 for laboratory`() {
        val typeSelection = lessonSearchSelector.getCourseTypeSelection(LABORATORY)

        assertThat(typeSelection).isEqualTo(3)
    }

    @Test
    fun `type selection should be 4 for project`() {
        val typeSelection = lessonSearchSelector.getCourseTypeSelection(PROJECT)

        assertThat(typeSelection).isEqualTo(4)
    }

    @Test
    fun `type selection should be 5 for language course`() {
        val typeSelection = lessonSearchSelector.getCourseTypeSelection(LANGUAGE_COURSE)

        assertThat(typeSelection).isEqualTo(5)
    }
}
