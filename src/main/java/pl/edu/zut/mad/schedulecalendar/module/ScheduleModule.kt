package pl.edu.zut.mad.schedulecalendar.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.realm.Realm
import pl.edu.zut.mad.schedulecalendar.*
import javax.inject.Singleton


@Module
class ScheduleModule(private val scheduleView: ScheduleMvp.View) {

    @Provides
    @Singleton
    fun provideUserPreferences(application: Application): SharedPreferences =
            application.getSharedPreferences(User.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUser(preferences: SharedPreferences) = User(preferences)

    @Provides
    @Singleton
    fun provideDatabase(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun provideRepository(database: Realm) = ScheduleRepository(database)

    @Provides
    @Singleton
    fun provideSchedulePresenter(scheduleRepository: ScheduleRepository) =
            SchedulePresenter(scheduleRepository, scheduleView)
}
