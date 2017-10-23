package pl.edu.zut.mad.schedule.module

import android.app.Application
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.NetworkConnection
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideService(): ScheduleService {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ScheduleService.BASE_URL)
                .build()
                .create(ScheduleService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(app: Application) = NetworkConnection(app)
}
