package pl.edu.zut.mad.schedule.search

import android.support.annotation.StringRes
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal interface SearchMvp {

    interface View {
        fun getTeacherName(): String

        fun getTeacherSurname(): String

        fun getFacultyAbbreviation(): String

        fun getSubject(): String

        fun getFieldOfStudy(): String

        fun getCourseType(): String

        fun getSemester(): Int?

        fun getForm(): String

        fun getDateFrom(): String

        fun getDateTo(): String

        fun onScheduleDownloaded(lessons: List<Lesson>)

        fun showLoading()

        fun hideLoading()

        fun showError(@StringRes errorRes: Int)
    }

    interface Presenter {
        fun onSearch()
    }
}
