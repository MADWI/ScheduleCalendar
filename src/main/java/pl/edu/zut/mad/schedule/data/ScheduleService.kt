package pl.edu.zut.mad.schedule.data

import io.reactivex.Observable
import pl.edu.zut.mad.schedule.data.model.api.Day
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface ScheduleService {

    @GET("{albumNumber}")
    fun fetchScheduleByAlbumNumber(@Path("albumNumber") albumNumber: Int): Observable<List<Day>>

    @GET(".")
    fun fetchScheduleByQueries(@QueryMap queries: Map<String, String>): Observable<List<Day>>

    companion object {
        const val BASE_URL = "http://uxplan.wi.zut.edu.pl/api/schedule/"
    }
}
