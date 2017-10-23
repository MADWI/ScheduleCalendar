package pl.edu.zut.mad.schedule.module

import dagger.Module
import dagger.Provides
import io.realm.Realm
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.data.ScheduleRepository
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
