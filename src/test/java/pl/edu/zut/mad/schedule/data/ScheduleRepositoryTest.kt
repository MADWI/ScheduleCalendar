package pl.edu.zut.mad.schedule.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import io.realm.Realm
import io.realm.RealmQuery
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

@RunWith(DataProviderRunner::class)
internal class ScheduleRepositoryTest {

    val date = LocalDate.now()
    val realm: Realm = mock()
    val modelMapper: ModelMapper = mock()
    val dayRealmQuery: RealmQuery<DayApi> = mock()
    val scheduledatabase: ScheduleDatabase = mock()

    @InjectMocks
    lateinit var repository: ScheduleRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(scheduledatabase.instance).thenReturn(realm)
        whenever(realm.where(DayApi::class.java)).thenReturn(dayRealmQuery)
        whenever(dayRealmQuery.equalTo(ScheduleRepository.DATE_COLUMN, date.toDate())).thenReturn(dayRealmQuery)
    }

    @Test
    fun getDayByDateShouldReturnEmptyDayWhenResultIsNull() {
        whenever(dayRealmQuery.findFirst()).thenReturn(null)

        val day = repository.getDayByDate(date).blockingFirst()

        assertThat(day).isInstanceOf(EmptyDay::class.java)
    }

    @Test
    @UseDataProvider("dayApiAndUi", location = arrayOf(MockData::class))
    fun getDayByDateShouldReturnDayWhenResultIsNotNull(dayApi: DayApi, dayUi: DayUi) {
        whenever(dayRealmQuery.findFirst()).thenReturn(dayApi)
        whenever(modelMapper.toDayUiFromApi(dayApi)).thenReturn(dayUi)

        val day = repository.getDayByDate(date).blockingFirst()

        assertThat(day).isInstanceOf(DayUi::class.java)
    }
}
