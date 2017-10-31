package pl.edu.zut.mad.schedule.module

import android.app.Application
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.MessageProvider
import pl.edu.zut.mad.schedule.util.NetworkConnection
import javax.inject.Singleton


@Module
class UtilsModule{

    @Provides
    @Singleton
    fun provideDatesProvider() = DatesProvider()

    @Provides
    @Singleton
    fun provideMessageProvider() = MessageProvider()

    @Provides
    @Singleton
    fun provideNetworkConnection(app: Application) = NetworkConnection(app)
}
