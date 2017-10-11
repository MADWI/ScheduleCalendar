package pl.edu.zut.mad.schedulecalendar

import android.app.Application
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ScheduleAppModule::class))
interface ScheduleAppComponent {

    fun inject(app: Application)

    fun plus(scheduleCalendarModule: ScheduleCalendarModule): ScheduleCalendarComponent
}
