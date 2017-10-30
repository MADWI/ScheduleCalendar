package pl.edu.zut.mad.schedule.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.User


@Module
class UserModule {

    @Provides
    fun provideUserPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences(User.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    @Provides
    fun provideUser(preferences: SharedPreferences) = User(preferences)
}
