package pl.edu.zut.mad.schedule.search

interface SearchMvp {

    interface View {
        fun getTeacherName(): String

        fun getTeacherSurname(): String

        fun getFacultyAbbreviation(): String

        fun getSubject(): String
    }

    interface Presenter {
        fun onSearch()
    }
}
