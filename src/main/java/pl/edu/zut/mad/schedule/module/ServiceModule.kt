package pl.edu.zut.mad.schedule.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.edu.zut.mad.schedule.data.ScheduleService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
internal class ServiceModule {

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
    }

    @Provides
    fun provideService(gSon: Gson, client: OkHttpClient): ScheduleService =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .client(client)
            .baseUrl(ScheduleService.BASE_URL)
            .build()
            .create(ScheduleService::class.java)

    @Provides
    fun provideGSon(): Gson = GsonBuilder()
        .setDateFormat(DATE_FORMAT)
        .create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
}
