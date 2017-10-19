package pl.edu.zut.mad.schedulecalendar.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day
import pl.edu.zut.mad.schedulecalendar.util.TextProvider


class LoginPresenter(private val view: LoginMvp.View, private val repository: ScheduleRepository,
                     private val service: ScheduleService, private val textProvider: TextProvider,
                     private val user: User) : LoginMvp.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun fetchScheduleForAlbumNumber(albumNumber: Int) {
        val disposable = service.fetchScheduleByAlbumNumber(albumNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnComplete { view.hideLoading() }
                .subscribe(
                        { saveSchedule(it, albumNumber) },
                        { onError(it) }
                )
        compositeDisposable.add(disposable)
    }

    private fun saveSchedule(days: List<Day>, albumNumber: Int) {
        repository.saveSchedule(days,
                { onScheduleSaved(albumNumber) },
                { onError(it) }
        )
    }

    private fun onScheduleSaved(albumNumber: Int) {
        user.save(albumNumber)
        view.onDataSaved()
    }

    private fun onError(error: Throwable) {
        val errorMessage = textProvider.getErrorMessageByThrowable(error)
        view.showError(errorMessage)
        view.hideLoading()
    }

    override fun cancelFetch() = compositeDisposable.clear()
}
