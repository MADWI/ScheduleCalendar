package pl.edu.zut.mad.schedulecalendar.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day
import pl.edu.zut.mad.schedulecalendar.module.TextProvider


class LoginPresenter(private val view: LoginMvp.View, private val repository: ScheduleRepository,
                     private val service: ScheduleService, private val textProvider: TextProvider)
    : LoginMvp.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var albumNumber = 0

    override fun fetchScheduleForAlbumNumber(albumNumber: Int) {
        this.albumNumber = albumNumber
        val disposable = service.fetchScheduleByAlbumNumber(albumNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoading() }
                .doOnComplete { view.hideLoading() }
                .subscribe(
                        { saveSchedule(it) },
                        { onError(it) }
                )
        compositeDisposable.add(disposable)
    }

    private fun saveSchedule(days: List<Day>) {
        repository.saveSchedule(days)
        view.onDataSaved(albumNumber)
    }

    private fun onError(error: Throwable) {
        val errorMessage = textProvider.getErrorMessageByThrowable(error)
        view.showError(errorMessage)
        view.hideLoading()
    }

    override fun cancelFetch() = compositeDisposable.clear()
}
