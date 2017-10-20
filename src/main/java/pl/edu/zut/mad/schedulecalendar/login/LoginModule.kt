package pl.edu.zut.mad.schedulecalendar.login

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.module.RepositoryModule
import pl.edu.zut.mad.schedulecalendar.module.ServiceModule
import pl.edu.zut.mad.schedulecalendar.module.UserModule
import pl.edu.zut.mad.schedulecalendar.util.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.util.TextProvider
import javax.inject.Singleton


@Module(includes = arrayOf(
        UserModule::class,
        ServiceModule::class,
        RepositoryModule::class
))
class LoginModule(private val view: LoginMvp.View, private val context: Context) {

    @Provides
    @Singleton
    fun provideTextProvider() = TextProvider()

    @Provides
    @Singleton
    fun provideLoginPresenter(service: ScheduleService, repository: ScheduleRepository,
                              textProvider: TextProvider, networkUtils: NetworkUtils,
                              user: User): LoginMvp.Presenter =
            LoginPresenter(view, repository, service, textProvider, user, networkUtils)
}
