package pl.edu.zut.mad.schedule.module

import dagger.Subcomponent
import pl.edu.zut.mad.schedule.ScheduleFragment
import pl.edu.zut.mad.schedule.animation.AnimationModule

@Schedule
@Subcomponent(modules = [
    ScheduleModule::class,
    AnimationModule::class,
    RepositoryModule::class
])
interface ScheduleComponent {

    fun inject(scheduleFragment: ScheduleFragment)
}
