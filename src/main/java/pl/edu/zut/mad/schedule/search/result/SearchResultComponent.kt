package pl.edu.zut.mad.schedule.search.result

import dagger.Component
import pl.edu.zut.mad.schedule.search.SearchResultsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AnimationModule::class])
internal interface SearchResultComponent {

    fun inject(searchResultsFragment: SearchResultsFragment)
}
