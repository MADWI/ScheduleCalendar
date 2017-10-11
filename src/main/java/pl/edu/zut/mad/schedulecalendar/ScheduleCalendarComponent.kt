package pl.edu.zut.mad.schedulecalendar

import dagger.Component
import pl.edu.zut.mad.schedulecalendar.ui.LessonsFragment
import pl.edu.zut.mad.schedulecalendar.ui.SchedulePagerFragment
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ScheduleCalendarModule::class))
interface ScheduleCalendarComponent {

    fun inject(schedulePagerFragment: SchedulePagerFragment)

    fun inject(lessonsFragment: LessonsFragment)
}
