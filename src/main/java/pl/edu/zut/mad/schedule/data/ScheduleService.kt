package pl.edu.zut.mad.schedule.data

import io.reactivex.Observable
import pl.edu.zut.mad.schedule.data.model.api.Day
import retrofit2.http.GET
import retrofit2.http.Path


internal interface ScheduleService {

    @GET("{albumNumber}")
    fun fetchScheduleByAlbumNumber(@Path("albumNumber") albumNumber: Int): Observable<List<Day>>

    companion object {
        const val BASE_URL = "http://uxplan.wi.zut.edu.pl/api/schedule/"
    }
}
