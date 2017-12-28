package pl.edu.zut.mad.schedule.module

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.util.ModelMapper

@Module
internal class RepositoryModule {

    @Provides
    fun provideRepository(mapper: ModelMapper) =
            ScheduleRepository(mapper)
}
