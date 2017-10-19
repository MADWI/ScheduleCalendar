package pl.edu.zut.mad.schedulecalendar.module

import android.app.Application
import dagger.Component
import pl.edu.zut.mad.schedulecalendar.login.LoginComponent
import pl.edu.zut.mad.schedulecalendar.login.LoginModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ScheduleAppModule::class))
interface ScheduleAppComponent {

    fun inject(app: Application)

    fun plus(loginModule: LoginModule): LoginComponent

    fun plus(scheduleModule: ScheduleModule): ScheduleComponent
}
