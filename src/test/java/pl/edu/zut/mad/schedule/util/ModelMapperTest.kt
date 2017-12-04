package pl.edu.zut.mad.schedule.util

import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.LocalDate
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.zut.mad.schedule.MockData
import pl.edu.zut.mad.schedule.MockData.Companion.DATE_DAY
import pl.edu.zut.mad.schedule.MockData.Companion.DATE_MONTH
import pl.edu.zut.mad.schedule.MockData.Companion.DATE_YEAR
import pl.edu.zut.mad.schedule.MockData.Companion.SUBJECT_WITH_TYPE
import pl.edu.zut.mad.schedule.MockData.Companion.TEACHER_WITH_ROOM
import pl.edu.zut.mad.schedule.MockData.Companion.TIME_END
import pl.edu.zut.mad.schedule.MockData.Companion.TIME_START
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi

@Suppress("IllegalIdentifier")
@RunWith(DataProviderRunner::class)
internal class ModelMapperTest {

    private val modelMapper = ModelMapper()

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui date is equal to day api date`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)

        assertThat(dayUi.date.dayOfMonth).isEqualTo(DATE_DAY)
    }

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui month is equal to day api month`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)

        assertThat(dayUi.date.monthOfYear).isEqualTo(DATE_MONTH)
    }

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui year is equal to day api year`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)

        assertThat(dayUi.date.year).isEqualTo(DATE_YEAR)
    }

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui has proper subject with type`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.subjectWithType).isEqualTo(SUBJECT_WITH_TYPE)
    }

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui has proper teacher with room`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.teacherWithRoom).isEqualTo(TEACHER_WITH_ROOM)
    }

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui time range start is equal to time start`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.timeRange.start).isEqualTo(TIME_START)
    }

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui time range end is equal to time end`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.timeRange.end).isEqualTo(TIME_END)
    }

    @Test
    @UseDataProvider("dayApi", location = [(MockData::class)])
    fun `day ui lessons is cancelled is false`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.isCancelled).isFalse()
    }

    @Test
    @UseDataProvider("dayApiWithCancelledLesson", location = [(MockData::class)])
    fun `lesson is cancelled is true`(dayApi: DayApi) {
        val dayUi = modelMapper.toDayUiFromApi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.isCancelled).isTrue()
    }

    @Test
    @UseDataProvider("dayUi", location = [(MockData::class)])
    fun `lesson has event is true`(dayUi: DayUi) {
        val lessonEvents = modelMapper.toLessonsEventsFromDayUi(dayUi)

        assertThat(lessonEvents[0].hasEvent()).isTrue()
    }

    @Test
    fun `empty day event is null`() {
        val date = LocalDate.now()
        val emptyDay = EmptyDay(date)

        val lessonEvent = modelMapper.toLessonEventFromEmptyDay(emptyDay)

        assertThat(lessonEvent.event).isNull()
    }

    @Test
    fun `empty day has event is false`() {
        val date = LocalDate.now()
        val emptyDay = EmptyDay(date)

        val lessonEvent = modelMapper.toLessonEventFromEmptyDay(emptyDay)

        assertThat(lessonEvent.hasEvent()).isFalse()
    }

    @Test
    @UseDataProvider("dayApiList", location = [(MockData::class)])
    fun `ui lessons should consist of lesson from api days`(daysApi: List<DayApi>) {
        val expectedLessonsCount = daysApi.map { it.lessons }.sumBy { it.size }

        val lessons = modelMapper.toUiLessons(daysApi)

        assertThat(lessons.size).isEqualTo(expectedLessonsCount)
    }
}
