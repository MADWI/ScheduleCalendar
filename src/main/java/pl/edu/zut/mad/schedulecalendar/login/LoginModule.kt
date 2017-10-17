package pl.edu.zut.mad.schedulecalendar.login

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.module.*
import pl.edu.zut.mad.schedulecalendar.util.TextProvider
import pl.edu.zut.mad.schedulecalendar.util.NetworkUtils
import javax.inject.Singleton


@Module(includes = arrayOf(
        UserModule::class,
        ServiceModule::class,
        RepositoryModule::class
))
class LoginModule(private val view: LoginMvp.View, private val context: Context) {

    @Provides
    @Singleton
    fun provideNetworkUtils() = NetworkUtils()

    @Provides
    @Singleton
    fun provideTextProvider() = TextProvider(context)

    @Provides
    @Singleton
    fun provideLoginPresenter(service: ScheduleService, repository: ScheduleRepository,
                              textProvider: TextProvider) =
            LoginPresenter(view, repository, service, textProvider)
}
