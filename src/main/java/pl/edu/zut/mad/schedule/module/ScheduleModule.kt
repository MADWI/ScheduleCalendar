package pl.edu.zut.mad.schedule.module

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.ScheduleMvp
import pl.edu.zut.mad.schedule.SchedulePresenter
import pl.edu.zut.mad.schedule.User
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection
import javax.inject.Singleton


@Module
class ScheduleModule(private val scheduleView: ScheduleMvp.View) {

    @Provides
    @Singleton
    fun provideSchedulePresenter(scheduleRepository: ScheduleRepository, mapper: ModelMapper,
                                 networkConnection: NetworkConnection, user: User) =
            SchedulePresenter(scheduleRepository, user, scheduleView, mapper, networkConnection)
}
