package pl.edu.zut.mad.schedule.search

import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal interface SearchMvp {

    interface View {
        fun getTeacherName(): String

        fun getTeacherSurname(): String

        fun getFacultyAbbreviation(): String

        fun getSubject(): String

        fun getDateFrom(): LocalDate

        fun getDateTo(): LocalDate

        fun onScheduleDownloaded(lessons: List<Lesson>)

        fun showLoading()

        fun hideLoading()

        fun showError()
    }

    interface Presenter {
        fun onSearch()
    }
}
