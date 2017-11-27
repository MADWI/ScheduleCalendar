package pl.edu.zut.mad.schedule.search

import dagger.Component
import pl.edu.zut.mad.schedule.module.ServiceModule

@Component(modules = arrayOf(SearchModule::class, ServiceModule::class))
internal interface SearchComponent {

    fun inject(searchActivity: SearchActivity)
}
