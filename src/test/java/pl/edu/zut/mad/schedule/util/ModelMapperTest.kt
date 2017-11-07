package pl.edu.zut.mad.schedule.util

import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.zut.mad.schedule.MockData
import pl.edu.zut.mad.schedule.MockData.Companion.ACADEMIC_TITLE
import pl.edu.zut.mad.schedule.MockData.Companion.DATE_DAY
import pl.edu.zut.mad.schedule.MockData.Companion.DATE_MONTH
import pl.edu.zut.mad.schedule.MockData.Companion.DATE_YEAR
import pl.edu.zut.mad.schedule.MockData.Companion.ROOM
import pl.edu.zut.mad.schedule.MockData.Companion.SUBJECT
import pl.edu.zut.mad.schedule.MockData.Companion.TEACHER_NAME
import pl.edu.zut.mad.schedule.MockData.Companion.TEACHER_SURNAME
import pl.edu.zut.mad.schedule.MockData.Companion.TIME_END
import pl.edu.zut.mad.schedule.MockData.Companion.TIME_START
import pl.edu.zut.mad.schedule.MockData.Companion.TYPE
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi

@RunWith(DataProviderRunner::class)
internal class ModelMapperTest {

    private lateinit var modelMapper: ModelMapper

    @Before
    fun setUp() {
        modelMapper = ModelMapper()
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperDay(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)

        assertThat(dayUi.date.dayOfMonth).isEqualTo(DATE_DAY)
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperMonth(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)

        assertThat(dayUi.date.monthOfYear).isEqualTo(DATE_MONTH)
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperYear(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)

        assertThat(dayUi.date.year).isEqualTo(DATE_YEAR)
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperLessonSubjectWithType(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.subjectWithType).isEqualTo("$SUBJECT ($TYPE)")
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperLessonTeacherWithRoom(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.teacherWithRoom)
            .isEqualTo("$ACADEMIC_TITLE $TEACHER_NAME $TEACHER_SURNAME $ROOM")
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperLessonTimeStart(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.timeRange.from).isEqualTo(TIME_START)
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperLessonTimeEnd(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.timeRange.to).isEqualTo(TIME_END)
    }

    @Test
    @UseDataProvider("dayApi", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperLessonNotCancelled(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.isCancelled).isFalse()
    }

    @Test
    @UseDataProvider("dayApiWithCancelledLesson", location = arrayOf(MockData::class))
    fun mapFromApiToUiSetProperLessonCancelled(dayApi: DayApi) {
        val dayUi = modelMapper.dayFromApiToUi(dayApi)
        val lesson = dayUi.lessons[0]

        assertThat(lesson.isCancelled).isTrue()
    }

    @Test
    @UseDataProvider("dayUi", location = arrayOf(MockData::class))
    fun lessonsEventHasEventReturnTrue(dayUi: DayUi) {
        val lessonEvents = modelMapper.toLessonsEvents(dayUi)

        assertThat(lessonEvents[0].hasEvent()).isTrue()
    }

    @Test
    fun emptyDayHasNullEvent() {
        val date = LocalDate.now()
        val emptyDay = EmptyDay(date)

        val lessonEvent = modelMapper.toLessonEvent(emptyDay)

        assertThat(lessonEvent.event).isNull()
    }

    @Test
    fun emptyDayHasEventReturnFalse() {
        val date = LocalDate.now()
        val emptyDay = EmptyDay(date)

        val lessonEvent = modelMapper.toLessonEvent(emptyDay)

        assertThat(lessonEvent.hasEvent()).isFalse()
    }
}
