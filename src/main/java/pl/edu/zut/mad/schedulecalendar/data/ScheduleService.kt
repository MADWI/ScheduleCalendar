package pl.edu.zut.mad.schedulecalendar.data

import io.reactivex.Observable
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day
import retrofit2.http.GET
import retrofit2.http.Path


interface ScheduleService {

    @GET("{albumNumber}")
    fun fetchScheduleByAlbumNumber(@Path("albumNumber") albumNumber: Int): Observable<List<Day>>

    companion object {
        const val BASE_URL = "http://uxplan.wi.zut.edu.pl/api/schedule/"
    }
}
