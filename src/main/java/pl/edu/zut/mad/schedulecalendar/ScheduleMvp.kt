package pl.edu.zut.mad.schedulecalendar


internal interface ScheduleMvp {

    interface View {
        fun showLoading()

        fun hiderLoading()

        fun showError()
    }

    interface Presenter {
        fun fetchLessons()
    }
}
