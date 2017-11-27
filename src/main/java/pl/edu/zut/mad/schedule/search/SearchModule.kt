package pl.edu.zut.mad.schedule.search

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.module.ServiceModule
import pl.edu.zut.mad.schedule.module.UtilsModule
import pl.edu.zut.mad.schedule.util.ModelMapper

@Module(includes = arrayOf(ServiceModule::class, UtilsModule::class))
internal class SearchModule(private val view: SearchMvp.View) {

    @Provides
    fun providePresenter(service: ScheduleService, modelMapper: ModelMapper): SearchMvp.Presenter =
        SearchPresenter(view, service, modelMapper)
}
