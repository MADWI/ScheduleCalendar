package pl.edu.zut.mad.schedulecalendar.module

import dagger.Subcomponent
import pl.edu.zut.mad.schedulecalendar.ui.ScheduleFragment
import pl.edu.zut.mad.schedulecalendar.ui.ScheduleFragmentAgenda
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = arrayOf(UserModule::class))
interface UserComponent {

    fun inject(scheduleFragment: ScheduleFragment)

    fun inject(scheduleFragment: ScheduleFragmentAgenda)
}
