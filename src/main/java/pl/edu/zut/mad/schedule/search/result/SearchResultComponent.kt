package pl.edu.zut.mad.schedule.search.result

import dagger.Subcomponent
import pl.edu.zut.mad.schedule.animation.AnimationModule
import pl.edu.zut.mad.schedule.search.SearchResultsFragment

@Subcomponent(modules = [AnimationModule::class])
internal interface SearchResultComponent {

    fun inject(searchResultsFragment: SearchResultsFragment)
}
