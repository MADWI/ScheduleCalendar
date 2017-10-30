package pl.edu.zut.mad.schedule.login

import dagger.Subcomponent


@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent {

    fun inject(loginActivity: LoginActivity)
}
