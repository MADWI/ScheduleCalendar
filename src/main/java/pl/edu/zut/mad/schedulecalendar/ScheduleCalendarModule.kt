package pl.edu.zut.mad.schedulecalendar

import dagger.Module
import dagger.Provides
import io.realm.Realm
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
