package pl.edu.zut.mad.schedulecalendar.login

import dagger.Module
import dagger.Provides
import io.realm.Realm
import pl.edu.zut.mad.schedulecalendar.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.api.ScheduleService
import javax.inject.Singleton


@Module
class LoginModule(val view: LoginMvp.View) {

    @Provides
    @Singleton
    fun provideDatabase(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun provideScheduleService() = ScheduleService.create() // TODO

    @Provides
    @Singleton
    fun provideScheduleRepository(database: Realm) = ScheduleRepository(database)

    @Provides
    @Singleton
    fun provideLoginPresenter(service: ScheduleService, repository: ScheduleRepository) =
            LoginPresenter(view, service, repository)
}
