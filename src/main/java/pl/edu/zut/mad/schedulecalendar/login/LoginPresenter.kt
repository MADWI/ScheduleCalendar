package pl.edu.zut.mad.schedulecalendar.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day


class LoginPresenter(private val view: LoginMvp.View,
                     private val service: ScheduleService,
                     private val repository: ScheduleRepository) : LoginMvp.Presenter {

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

    private fun onError(it: Throwable) {
        view.showError(it.message)
    }

    override fun cancelFetch() = compositeDisposable.clear()
}
