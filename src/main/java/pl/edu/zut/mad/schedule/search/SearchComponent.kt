package pl.edu.zut.mad.schedule.search

import dagger.Subcomponent
import pl.edu.zut.mad.schedule.module.ServiceModule

@Search
@Subcomponent(modules = arrayOf(
    SearchModule::class,
    ServiceModule::class
))
internal interface SearchComponent {

    fun inject(searchInputFragment: SearchInputFragment)
}
