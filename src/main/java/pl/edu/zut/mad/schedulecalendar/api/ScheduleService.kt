package pl.edu.zut.mad.schedulecalendar.api

import io.reactivex.Observable
import pl.edu.zut.mad.schedulecalendar.model.db.Day
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ScheduleService {

    @GET("{albumNumber}")
    fun fetchScheduleByAlbumNumber(@Path("albumNumber") albumNumber: Int): Observable<List<Day>>

    companion object { // TODO: move to dagger module
        private const val BASE_URL = "http://uxplan.wi.zut.edu.pl/api/schedule/"
        fun create(): ScheduleService {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(ScheduleService::class.java)
        }
    }
}
