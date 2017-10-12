package pl.edu.zut.mad.schedulecalendar

import dagger.Subcomponent
import pl.edu.zut.mad.schedulecalendar.ui.LessonsFragment
import pl.edu.zut.mad.schedulecalendar.ui.LessonsPagerFragment
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = arrayOf(ScheduleCalendarModule::class))
interface ScheduleCalendarComponent {

    fun inject(lessonsFragment: LessonsFragment)

    fun inject(lessonsPagerFragment: LessonsPagerFragment)
}
