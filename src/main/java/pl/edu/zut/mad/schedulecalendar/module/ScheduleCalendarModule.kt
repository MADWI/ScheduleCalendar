package pl.edu.zut.mad.schedulecalendar.module

import dagger.Module
import dagger.Provides
import io.realm.Realm
import pl.edu.zut.mad.schedulecalendar.ScheduleRepository
import javax.inject.Singleton


@Module
class ScheduleCalendarModule {

    @Provides
    @Singleton
    fun provideDatabase(): Realm = Realm.getDefaultInstance() // TODO: check by @Inject constructor

    @Provides
    @Singleton
    fun provideRepository(database: Realm) = ScheduleRepository(database)
}
