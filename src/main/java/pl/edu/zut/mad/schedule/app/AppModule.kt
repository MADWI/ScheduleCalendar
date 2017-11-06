package pl.edu.zut.mad.schedule.app

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule(private val app: Application) : Application() {

    @Provides
    @Singleton
    fun provideApplication() = app
}
