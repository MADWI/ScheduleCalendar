package pl.edu.zut.mad.schedule.module

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.util.DatesProvider
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection
import java.util.Locale
import javax.inject.Singleton

@Module
internal class UtilsModule {

    companion object {
        private const val POLISH_LANGUAGE_CODE = "pl"
    }

    @Provides
    @Singleton
    fun provideMapper() = ModelMapper()

    @Provides
    @Singleton
    fun provideDatesProvider() = DatesProvider()

    @Provides
    @Singleton
    fun provideNetworkConnection(app: Application) = NetworkConnection(app)

    @Provides
    @Singleton
    fun providePolandResources(app: Application): Resources {
        val configuration = Configuration(app.resources.configuration)
        configuration.setLocale(Locale(POLISH_LANGUAGE_CODE))
        val localizedContext = app.createConfigurationContext(configuration)
        return localizedContext.resources
    }
}
