package pl.edu.zut.mad.schedule.widget

import dagger.Component
import pl.edu.zut.mad.schedule.module.RepositoryModule

@Component(modules = arrayOf(RepositoryModule::class))
internal interface WidgetComponent {

    fun inject(lessonsRemoteViewsFactory: LessonsRemoteViewsFactory)
}
