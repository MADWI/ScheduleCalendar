package pl.edu.zut.mad.schedule.login

import dagger.Subcomponent
import pl.edu.zut.mad.schedule.module.RepositoryModule
import pl.edu.zut.mad.schedule.module.ServiceModule

@Login
@Subcomponent(modules = arrayOf(
        LoginModule::class,
        ServiceModule::class,
        RepositoryModule::class)
)
internal interface LoginComponent {

    fun inject(loginActivity: LoginActivity)
}
