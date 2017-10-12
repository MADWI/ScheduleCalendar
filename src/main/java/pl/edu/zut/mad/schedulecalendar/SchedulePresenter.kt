package pl.edu.zut.mad.schedulecalendar


class SchedulePresenter(private val scheduleRepository: ScheduleRepository,
                        private val view: ScheduleMvp.View) : ScheduleMvp.Presenter {

    override fun fetchLessons() {
        view.showLoading()
        val lessonDays = scheduleRepository.getSchedule()
        view.setLessonsDays(lessonDays)
        view.hideLoading()
    }
}
