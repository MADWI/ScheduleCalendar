package pl.edu.zut.mad.schedulecalendar.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ScheduleAppModule(private val app: Application) : Application() {

    @Provides
    @Singleton
    fun provideApplication() = app
}
