package pl.edu.zut.mad.schedule.login

import dagger.Subcomponent
import pl.edu.zut.mad.schedule.module.RepositoryModule
import pl.edu.zut.mad.schedule.module.ServiceModule
import pl.edu.zut.mad.schedule.module.UserModule


@Login
@Subcomponent(modules = arrayOf(
        UserModule::class,
        LoginModule::class,
        ServiceModule::class,
        RepositoryModule::class)
)
internal interface LoginComponent {

    fun inject(loginActivity: LoginActivity)
}
