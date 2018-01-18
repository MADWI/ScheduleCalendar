package pl.edu.zut.mad.schedule.widget

import dagger.Component
import pl.edu.zut.mad.schedule.module.RepositoryModule
import pl.edu.zut.mad.schedule.module.UtilsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RepositoryModule::class,
    UtilsModule::class
])
internal interface WidgetComponent {

    fun inject(lessonsRemoteViewsFactory: LessonsRemoteViewsFactory)
}
