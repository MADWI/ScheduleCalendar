package pl.edu.zut.mad.schedule.search

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.ModelMapper

internal class SearchPresenter(private val view: SearchMvp.View,
    private val service: ScheduleService, private val modelMapper: ModelMapper)
    : SearchMvp.Presenter {

    override fun onSearch() {
        val teacherName = view.getTeacherName()
        val teacherSurname = view.getTeacherSurname()
        val subject = view.getSubject()
        val faculty = view.getFacultyAbbreviation()
        service.fetchScheduleByQueries(teacherName, teacherSurname, faculty, subject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val lessons = modelMapper.toUiLessons(it)
                    view.onScheduleDownloaded(lessons)
                },
                {
                    Log.e("Presenter error", it.toString())
                }
            )
    }
}
