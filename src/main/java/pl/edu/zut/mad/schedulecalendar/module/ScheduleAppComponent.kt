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

    fun plus(scheduleCalendarModule: ScheduleCalendarModule): ScheduleCalendarComponent

    fun plus(userModule: UserModule): UserComponent

    fun plus(loginModule: LoginModule): LoginComponent
}
