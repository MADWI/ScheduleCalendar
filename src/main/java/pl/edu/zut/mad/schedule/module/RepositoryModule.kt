package pl.edu.zut.mad.schedule.module

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleDatabase
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.util.ModelMapper
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(database: ScheduleDatabase, mapper: ModelMapper) =
            ScheduleRepository(database, mapper)

    @Provides
    @Singleton
    fun provideDatabase() = ScheduleDatabase()

    @Provides
    @Singleton
    fun provideMapper() = ModelMapper()
}
