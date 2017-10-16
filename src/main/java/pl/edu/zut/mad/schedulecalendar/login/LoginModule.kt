package pl.edu.zut.mad.schedulecalendar.login

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.api.ScheduleService
import pl.edu.zut.mad.schedulecalendar.module.RepositoryModule
import javax.inject.Singleton


@Module(includes = arrayOf(RepositoryModule::class))
class LoginModule(val view: LoginMvp.View) {

    @Provides
    @Singleton
    fun provideScheduleService() = ScheduleService.create() // TODO

    @Provides
    @Singleton
    fun provideLoginPresenter(service: ScheduleService, repository: ScheduleRepository) =
            LoginPresenter(view, service, repository)
}
