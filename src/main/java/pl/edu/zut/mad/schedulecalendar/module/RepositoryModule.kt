package pl.edu.zut.mad.schedulecalendar.module

import dagger.Module
import dagger.Provides
import io.realm.Realm
import pl.edu.zut.mad.schedulecalendar.util.ModelMapper
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(database: Realm, mapper: ModelMapper) =
            ScheduleRepository(database, mapper)

    @Provides
    @Singleton
    fun provideDatabase(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun provideMapper() = ModelMapper()
}
