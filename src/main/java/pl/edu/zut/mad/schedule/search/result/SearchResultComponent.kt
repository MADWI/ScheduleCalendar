package pl.edu.zut.mad.schedule.search.result

import dagger.Component
import pl.edu.zut.mad.schedule.search.SearchResultsFragment

//@Singleton
@Component(modules = [SearchResultModule::class])
internal interface SearchResultComponent {

    fun inject(searchResultsFragment: SearchResultsFragment)
}
