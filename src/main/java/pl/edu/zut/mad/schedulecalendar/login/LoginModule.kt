package pl.edu.zut.mad.schedulecalendar.login

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.module.RepositoryModule
import pl.edu.zut.mad.schedulecalendar.module.ServiceModule
import pl.edu.zut.mad.schedulecalendar.module.UserModule
import pl.edu.zut.mad.schedulecalendar.util.NetworkConnection
import pl.edu.zut.mad.schedulecalendar.util.MessageProvider
import javax.inject.Singleton


@Module(includes = arrayOf(
        UserModule::class,
        ServiceModule::class,
        RepositoryModule::class
))
class LoginModule(private val view: LoginMvp.View) {

    @Provides
    @Singleton
    fun provideTextProvider() = MessageProvider()

    @Provides
    @Singleton
    fun provideLoginPresenter(service: ScheduleService, repository: ScheduleRepository,
                              messageProvider: MessageProvider, connection: NetworkConnection,
                              user: User): LoginMvp.Presenter =
            LoginPresenter(view, repository, service, connection, messageProvider, user)
}
