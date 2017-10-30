package pl.edu.zut.mad.schedule.module

import dagger.Subcomponent
import pl.edu.zut.mad.schedule.ScheduleFragment
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