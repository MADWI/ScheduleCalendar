package pl.edu.zut.mad.schedulecalendar.login

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.util.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.module.RepositoryModule
import pl.edu.zut.mad.schedulecalendar.module.ServiceModule
import pl.edu.zut.mad.schedulecalendar.module.UserModule
import javax.inject.Singleton


@Module(includes = arrayOf(RepositoryModule::class, ServiceModule::class, UserModule::class))
class LoginModule(val view: LoginMvp.View) {

    @Provides
    @Singleton
    fun provideNetworkUtils() = NetworkUtils()

    @Provides
    @Singleton
    fun provideLoginPresenter(service: ScheduleService, repository: ScheduleRepository) =
            LoginPresenter(view, service, repository)
}
