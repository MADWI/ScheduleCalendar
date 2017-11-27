package pl.edu.zut.mad.schedule.search

import android.util.Log
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedule.data.ScheduleService

internal class SearchPresenter(private val view: SearchMvp.View, private val service: ScheduleService)
    : SearchMvp.Presenter {

    override fun onSearch() {
        val teacherName = view.getTeacherName()
        val teacherSurname = view.getTeacherSurname()
        val subject = view.getSubject()
        val faculty = view.getFacultyAbbreviation()
        service.fetchScheduleByQueries(teacherName, teacherSurname, faculty, subject)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("Presenter", it.toString())
                },
                {
                    Log.d("Presenter", it.toString())
                }
            )
    }
}
