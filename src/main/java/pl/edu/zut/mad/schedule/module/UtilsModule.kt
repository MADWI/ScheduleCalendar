package pl.edu.zut.mad.schedule.module

import android.app.Application
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.MessageProviderLogin
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection
import javax.inject.Singleton

@Module
internal class UtilsModule {

    @Provides
    @Singleton
    fun provideMapper() = ModelMapper()

    @Provides
    @Singleton
    fun provideDatesProvider() = DatesProvider()

    @Provides
    @Singleton
    fun provideMessageProviderLogin() = MessageProviderLogin()

    @Provides
    @Singleton
    fun provideNetworkConnection(app: Application) = NetworkConnection(app)
}
