package pl.edu.zut.mad.schedule.search

import dagger.Component
import pl.edu.zut.mad.schedule.module.ServiceModule

@Component(modules = arrayOf(SearchModule::class, ServiceModule::class))
interface SearchComponent {

    fun inject(searchByTeacherFragment: SearchFragment)
}
