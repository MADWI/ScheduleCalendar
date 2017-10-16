package pl.edu.zut.mad.schedulecalendar.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.reactivex.Observable
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedulecalendar.model.db.Day
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type


interface ScheduleService {

    @GET("{albumNumber}")
    fun fetchScheduleByAlbumNumber(@Path("albumNumber") albumNumber: Int): Observable<List<Day>>

    companion object { // TODO: move to dagger module
        private const val BASE_URL = "http://uxplan.wi.zut.edu.pl/api/schedule/"
        fun create(): ScheduleService { // TODO: remove due to separate model for api and ui\
            val gSon = GsonBuilder()
                    .setDateFormat("dd-MM-yyyy")
                    .registerTypeAdapter(LocalDate::class.java, LocalDateTypeConverter())
                    .create()
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gSon))
                    .baseUrl(BASE_URL)
                    .build()
                    .create(ScheduleService::class.java)
        }
    }

    class LocalDateTypeConverter : JsonDeserializer<LocalDate> {
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate =
                LocalDate.parse(json.asString, DateTimeFormat.forPattern("dd-MM-yyyy"))
    }
}
