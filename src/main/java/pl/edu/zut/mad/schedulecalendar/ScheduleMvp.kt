package pl.edu.zut.mad.schedulecalendar

import pl.edu.zut.mad.schedulecalendar.model.Day


interface ScheduleMvp {

    interface View {
        fun showLoading()

        fun hideLoading()

        fun setLessonsDays(lessonsDays: List<Day>)

        fun showError()
    }

    interface Presenter {
        fun fetchLessons()
    }
}
