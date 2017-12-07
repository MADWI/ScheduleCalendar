package pl.edu.zut.mad.schedule.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.data.model.api.Day
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

internal class SearchPresenter(private val view: SearchMvp.View,
    private val service: ScheduleService, private val modelMapper: ModelMapper,
    private val networkConnection: NetworkConnection, private val messageProvider: MessageProviderSearch)
    : SearchMvp.Presenter {

    companion object {
        private const val QUERY_TEACHER_NAME = "name"
        private const val QUERY_TEACHER_SURNAME = "surname"
        private const val QUERY_FACULTY_ABBREVIATION = "facultyAbbreviation"
        private const val QUERY_SUBJECT = "subject"
        private const val QUERY_FIELD_OF_STUDY = "fieldOfStudy"
        private const val QUERY_COURSE_TYPE = "courseType"
        private const val QUERY_SEMESTER = "semester"
        private const val QUERY_FORM = "form"
        private const val QUERY_DATE_FROM = "dateFrom"
        private const val QUERY_DATE_TO = "dateTo"
    }

    private fun getSearchQueryFromModel(searchInput: SearchInputViewModel): Map<String, String> {
        val query = HashMap<String, String>()
        query.put(QUERY_TEACHER_NAME, searchInput.teacherName)
        query.put(QUERY_TEACHER_SURNAME, searchInput.teacherName)
        query.put(QUERY_FACULTY_ABBREVIATION, searchInput.facultyAbbreviation)
        query.put(QUERY_SUBJECT, searchInput.subject)
        query.put(QUERY_FIELD_OF_STUDY, searchInput.fieldOfStudy)
        query.put(QUERY_COURSE_TYPE, searchInput.courseType)
        query.put(QUERY_SEMESTER, searchInput.semester)
        query.put(QUERY_FORM, searchInput.form)
        query.put(QUERY_DATE_FROM, searchInput.dateFrom)
        query.put(QUERY_DATE_TO, searchInput.dateTo)
        return query
    }

    override fun onSearch() {
        view.showLoading()
        if (!networkConnection.isAvailable()) {
            view.hideLoading()
            view.showError(R.string.error_no_internet)
            return
        }
        val searchQuery = getSearchQueryFromModel(view.getSearchQuery())
        service.fetchScheduleByQueries(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showLessonsAndHideLoading(it) },
                { showErrorAndHideLoading(it) }
            )
    }

    private fun showLessonsAndHideLoading(days: List<Day>) {
        view.hideLoading()
        val lessons = modelMapper.toUiLessons(days)
        view.setData(lessons)
    }

    private fun showErrorAndHideLoading(error: Throwable) {
        view.hideLoading()
        val errorResId = messageProvider.getResIdByError(error)
        view.showError(errorResId)
    }
}
