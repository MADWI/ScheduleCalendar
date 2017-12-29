package pl.edu.zut.mad.schedule.data

import io.reactivex.Observable
import pl.edu.zut.mad.schedule.data.model.api.Day
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

internal interface ScheduleService {

    companion object {
        const val BASE_URL = "http://uxplan.wi.zut.edu.pl/api/schedule/"
    }

    @GET("{albumNumber}")
    fun fetchScheduleByAlbumNumber(@Path("albumNumber") albumNumber: Int): Observable<List<Day>>

    @GET(".")
    fun fetchScheduleByQueries(@QueryMap queries: Map<String, String>): Observable<List<Day>>

    @GET("dictionary?limit=100&")
    fun fetchSurnames(@Query("filter") field: String, @Query("surname") surname: String): Observable<List<String>>
}
