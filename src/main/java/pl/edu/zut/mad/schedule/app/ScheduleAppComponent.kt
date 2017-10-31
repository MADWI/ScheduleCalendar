package pl.edu.zut.mad.schedule.app

import android.app.Application
import dagger.Component
import pl.edu.zut.mad.schedule.login.LoginComponent
import pl.edu.zut.mad.schedule.login.LoginModule
import pl.edu.zut.mad.schedule.module.ScheduleComponent
import pl.edu.zut.mad.schedule.module.ScheduleModule
import pl.edu.zut.mad.schedule.module.UtilsModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ScheduleAppModule::class, UtilsModule::class))
internal interface ScheduleAppComponent {

    fun inject(app: Application)

    fun plus(loginModule: LoginModule): LoginComponent

    fun plus(scheduleModule: ScheduleModule): ScheduleComponent
}
