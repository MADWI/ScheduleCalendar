package pl.edu.zut.mad.schedule.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.log
import retrofit2.HttpException

internal class SearchPresenter(private val view: SearchMvp.View,
    private val service: ScheduleService, private val modelMapper: ModelMapper)
    : SearchMvp.Presenter {

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
        private val DATE_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT) // TODO move to config class
    }

    override fun onSearch() {
        view.showLoading()
        val teacherName = view.getTeacherName()
        val teacherSurname = view.getTeacherSurname()
        val facultyAbbreviation = view.getFacultyAbbreviation()
        val subject = view.getSubject()
        val fieldOfStudy = view.getFieldOfStudy()
        val semester = view.getSemester()
        val form = view.getForm()
        val dateFrom = view.getDateFrom()
        val dateTo = view.getDateTo()
        service.fetchScheduleByQueries(teacherName, teacherSurname, facultyAbbreviation, subject,
            fieldOfStudy, semester, form,  dateFrom.toString(DATE_FORMATTER), dateTo.toString(DATE_FORMATTER))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { view.hideLoading() }
            .subscribe(
                {
                    val lessons = modelMapper.toUiLessons(it)
                    view.onScheduleDownloaded(lessons)
                },
                {
                    log("Error " + (it as HttpException).response()) // TODO loading and error
                }
            )
    }
}
