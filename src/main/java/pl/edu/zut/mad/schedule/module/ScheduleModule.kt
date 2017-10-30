package pl.edu.zut.mad.schedule.module

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.ScheduleMvp
import pl.edu.zut.mad.schedule.SchedulePresenter
import pl.edu.zut.mad.schedule.User
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection


@Module
class ScheduleModule(private val scheduleView: ScheduleMvp.View) {

    @Provides
    fun provideSchedulePresenter(repository: ScheduleRepository, datesProvider: DatesProvider,
                                 mapper: ModelMapper, connection: NetworkConnection, user: User) =
            SchedulePresenter(repository, mapper, scheduleView, datesProvider, user, connection)

    @Provides
    fun provideDatesProvider() = DatesProvider()
}
