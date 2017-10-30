package pl.edu.zut.mad.schedule.module

import android.app.Application
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.NetworkConnection
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ServiceModule {

    @Provides
    fun provideService(): ScheduleService {
        val gSon = GsonBuilder()
                .setDateFormat("dd-MM-yyyy")
                .create()
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gSon))
                .baseUrl(ScheduleService.BASE_URL)
                .build()
                .create(ScheduleService::class.java)
    }

    @Provides
    fun provideNetworkUtils(app: Application) = NetworkConnection(app)
}
