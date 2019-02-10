package pl.edu.zut.mad.schedule.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import pl.edu.zut.mad.schedule.MockData
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.model.api.Day as DayApi
import pl.edu.zut.mad.schedule.data.model.ui.Day as DayUi

@Suppress("IllegalIdentifier")
@RunWith(DataProviderRunner::class)
internal class ScheduleRepositoryTest {

    val date: LocalDate = LocalDate.now()
    val modelMapper: ModelMapper = mock()
    val database: ScheduleDatabase = mock()

    @InjectMocks
    lateinit var repository: ScheduleRepository

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `get day should return empty day when database result is null`() {
        whenever(database.findDayByDate(any())).thenReturn(null)

        val day = repository.getDayByDate(date).blockingFirst()

        assertThat(day).isInstanceOf(EmptyDay::class.java)
    }

    @Test
    @UseDataProvider("dayApiAndUi", location = [(MockData::class)])
    fun `get day should return day when result is not null`(dayApi: DayApi, dayUi: DayUi) {
        whenever(database.findDayByDate(any())).thenReturn(dayApi)
        whenever(modelMapper.toDayUiFromApi(dayApi)).thenReturn(dayUi)

        val day = repository.getDayByDate(date).blockingFirst()

        assertThat(day).isInstanceOf(DayUi::class.java)
    }
}
