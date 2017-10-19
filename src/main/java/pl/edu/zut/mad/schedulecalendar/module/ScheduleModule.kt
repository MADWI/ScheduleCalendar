package pl.edu.zut.mad.schedulecalendar.module

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.ScheduleMvp
import pl.edu.zut.mad.schedulecalendar.SchedulePresenter
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.util.NetworkUtils
import javax.inject.Singleton


@Module
class ScheduleModule(private val scheduleView: ScheduleMvp.View) {

    @Provides
    @Singleton
    fun provideSchedulePresenter(scheduleRepository: ScheduleRepository, user: User,
                                 networkUtils: NetworkUtils) =
            SchedulePresenter(scheduleRepository, user, scheduleView, networkUtils)
}
