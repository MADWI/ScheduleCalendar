package pl.edu.zut.mad.schedule.search

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.ModelMapper

@Module
internal class SearchModule(private val view: SearchMvp.View) {

    @Search
    @Provides
    fun providePresenter(service: ScheduleService, modelMapper: ModelMapper): SearchMvp.Presenter =
        SearchPresenter(view, service, modelMapper)
}
