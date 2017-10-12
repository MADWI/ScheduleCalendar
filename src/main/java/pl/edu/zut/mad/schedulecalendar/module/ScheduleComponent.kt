package pl.edu.zut.mad.schedulecalendar.module

import dagger.Subcomponent
import pl.edu.zut.mad.schedulecalendar.ui.ScheduleFragment
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = arrayOf(ScheduleModule::class))
interface ScheduleComponent {

    fun inject(scheduleFragment: ScheduleFragment)
}
