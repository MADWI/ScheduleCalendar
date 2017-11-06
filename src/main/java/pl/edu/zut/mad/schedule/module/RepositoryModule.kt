package pl.edu.zut.mad.schedule.module

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleDatabase
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.util.ModelMapper

@Module
internal class RepositoryModule {

    @Provides
    fun provideRepository(database: ScheduleDatabase, mapper: ModelMapper) =
            ScheduleRepository(database, mapper)

    @Provides
    fun provideDatabase() = ScheduleDatabase()

    @Provides
    fun provideMapper() = ModelMapper()
}
