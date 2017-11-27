package pl.edu.zut.mad.schedule.search

import pl.edu.zut.mad.schedule.data.model.ui.Day

internal interface SearchMvp {

    interface View {
        fun getTeacherName(): String

        fun getTeacherSurname(): String

        fun getFacultyAbbreviation(): String

        fun getSubject(): String

        fun onScheduleDownloaded(days: List<Day>)
    }

    interface Presenter {
        fun onSearch()
    }
}
