package pl.edu.zut.mad.schedulecalendar.module

import dagger.Module
import dagger.Provides
import io.realm.Realm
import pl.edu.zut.mad.schedulecalendar.ScheduleMvp
import pl.edu.zut.mad.schedulecalendar.SchedulePresenter
import pl.edu.zut.mad.schedulecalendar.ScheduleRepository
import javax.inject.Singleton


@Module
class ScheduleModule(private val scheduleView: ScheduleMvp.View) {

    @Provides
    @Singleton
    fun provideDatabase(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun provideRepository(database: Realm) = ScheduleRepository(database)

    @Provides
    @Singleton
    fun provideSchedulePresenter(scheduleRepository: ScheduleRepository) =
            SchedulePresenter(scheduleRepository, scheduleView)
}
