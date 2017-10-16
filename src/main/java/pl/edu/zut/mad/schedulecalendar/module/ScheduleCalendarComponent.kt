package pl.edu.zut.mad.schedulecalendar.module

import dagger.Subcomponent
import pl.edu.zut.mad.schedulecalendar.lesson.LessonsFragment
import pl.edu.zut.mad.schedulecalendar.lesson.LessonsPagerFragment
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = arrayOf(ScheduleCalendarModule::class))
interface ScheduleCalendarComponent {

    fun inject(lessonsFragment: LessonsFragment)

    fun inject(lessonsPagerFragment: LessonsPagerFragment)
}
