package pl.edu.zut.mad.schedulecalendar.module

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.ScheduleMvp
import pl.edu.zut.mad.schedulecalendar.SchedulePresenter
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import javax.inject.Singleton


@Module
class ScheduleModule(private val scheduleView: ScheduleMvp.View) {

    @Provides
    @Singleton
    fun provideSchedulePresenter(scheduleRepository: ScheduleRepository, user: User) =
            SchedulePresenter(scheduleRepository, user, scheduleView)
}
