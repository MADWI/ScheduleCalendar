package pl.edu.zut.mad.schedule.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.login.Login
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
internal class ServiceModule {

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
    }

    @Login
    @Provides
    fun provideService(gSon: Gson): ScheduleService =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gSon))
                    .baseUrl(ScheduleService.BASE_URL)
                    .build()
                    .create(ScheduleService::class.java)

    @Login
    @Provides
    fun provideGSon(): Gson = GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create()
}
