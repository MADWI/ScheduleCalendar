package pl.edu.zut.mad.schedulecalendar.module

import dagger.Subcomponent
import pl.edu.zut.mad.schedulecalendar.ScheduleFragment
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = arrayOf(
        UserModule::class,
        ServiceModule::class,
        ScheduleModule::class,
        RepositoryModule::class)
)
interface ScheduleComponent {

    fun inject(scheduleFragment: ScheduleFragment)
}
