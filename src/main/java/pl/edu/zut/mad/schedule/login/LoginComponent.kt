package pl.edu.zut.mad.schedule.login

import dagger.Subcomponent
import javax.inject.Singleton


@Singleton
@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent {

    fun inject(loginActivity: LoginActivity)
}
