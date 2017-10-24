package pl.edu.zut.mad.schedule.module

import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.util.ModelMapper
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(database: Realm, mapper: ModelMapper) =
            ScheduleRepository(database, mapper)

    @Provides
    @Singleton
    fun provideDatabase(): Realm =
            Observable.fromCallable { Realm.getDefaultInstance() }
                    .subscribeOn(Schedulers.single())
                    .blockingFirst()

    @Provides
    @Singleton
    fun provideMapper() = ModelMapper()
}
