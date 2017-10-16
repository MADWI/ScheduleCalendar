package pl.edu.zut.mad.schedulecalendar.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedulecalendar.User
import javax.inject.Singleton


@Module
class UserModule {

    @Provides
    @Singleton
    fun provideUserPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences(User.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUser(preferences: SharedPreferences) = User(preferences)
}
