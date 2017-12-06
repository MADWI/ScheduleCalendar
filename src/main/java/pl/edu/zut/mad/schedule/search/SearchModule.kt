package pl.edu.zut.mad.schedule.search

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.LessonIndexer
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

@Module
internal class SearchModule(private val view: SearchMvp.View) {

    @Search
    @Provides
    fun providePresenter(service: ScheduleService, modelMapper: ModelMapper,
        connection: NetworkConnection, messageProvider: MessageProviderSearch): SearchMvp.Presenter =
        SearchPresenter(view, service, modelMapper, connection, messageProvider)

    @Search
    @Provides
    fun provideMessageProviderSearch() = MessageProviderSearch()

    @Search
    @Provides
    fun provideLessonIndexer(polishResources: Resources) = LessonIndexer(polishResources)
}
