package pl.edu.zut.mad.schedulecalendar.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedulecalendar.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.api.ScheduleService
import pl.edu.zut.mad.schedulecalendar.model.db.Day


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
                .doOnNext { saveSchedule(it) }
                .doOnComplete { view.hideLoading() }
                .doOnError { view.showError(it.message) }
                .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun saveSchedule(days: List<Day>) {
        repository.saveSchedule(days)
        view.onDataSaved(albumNumber)
    }

    override fun cancelFetch() = compositeDisposable.clear()
}
