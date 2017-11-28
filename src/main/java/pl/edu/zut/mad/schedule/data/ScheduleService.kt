package pl.edu.zut.mad.schedule.data

import io.reactivex.Observable
import pl.edu.zut.mad.schedule.data.model.api.Day
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ScheduleService {

    @GET("{albumNumber}")
    fun fetchScheduleByAlbumNumber(@Path("albumNumber") albumNumber: Int): Observable<List<Day>>

    @GET(".")
    fun fetchScheduleByQueries(@Query("name") teacherName: String, @Query("surname") teacherSurname: String,
        @Query("facultyAbbreviation") facultyAbbreviation: String, @Query("subject") subject: String,
        @Query("dateFrom") dateFrom: String, @Query("dateTo") dateTo: String)
        : Observable<List<Day>>

    companion object {
        const val BASE_URL = "http://uxplan.wi.zut.edu.pl/api/schedule/"
    }
}
