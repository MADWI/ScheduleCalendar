package pl.edu.zut.mad.schedule.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.log

internal class SearchPresenter(private val view: SearchMvp.View,
    private val service: ScheduleService, private val modelMapper: ModelMapper)
    : SearchMvp.Presenter {

    override fun onSearch() {
        val teacherName = view.getTeacherName()
        val teacherSurname = view.getTeacherSurname()
        val subject = view.getSubject()
        val faculty = view.getFacultyAbbreviation()
        val dateFrom = view.getDateFrom()
        val dateTo = view.getDateTo()
        service.fetchScheduleByQueries(teacherName, teacherSurname, faculty, subject, dateFrom.toString(), dateTo.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val lessons = modelMapper.toUiLessons(it)
                    view.onScheduleDownloaded(lessons)
                },
                {
                    log("Error $it") // TODO loading and error
                }
            )
    }
}
