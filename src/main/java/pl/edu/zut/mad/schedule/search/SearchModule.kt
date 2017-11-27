package pl.edu.zut.mad.schedule.search

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.module.ServiceModule

@Module(includes = arrayOf(ServiceModule::class))
internal class SearchModule(private val view: SearchMvp.View) {

    @Provides
    fun providePresenter(service: ScheduleService): SearchMvp.Presenter =
        SearchPresenter(view, service)
}
