package pl.edu.zut.mad.schedulecalendar.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.data.ScheduleRepository
import pl.edu.zut.mad.schedulecalendar.data.ScheduleService
import pl.edu.zut.mad.schedulecalendar.data.model.db.Day
import pl.edu.zut.mad.schedulecalendar.util.NetworkConnection
import pl.edu.zut.mad.schedulecalendar.util.TextProvider


class LoginPresenter(private val view: LoginMvp.View, private val repository: ScheduleRepository,
                     private val service: ScheduleService, private val textProvider: TextProvider,
                     private val user: User, private val networkConnection: NetworkConnection)
    : LoginMvp.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onLoginClick() {
        val albumNumber = view.getAlbumNumberText()
        if (!validateAlbumNumber(albumNumber)) {
            return
        }
        if (!networkConnection.isAvailable()) {
            view.showError(R.string.error_no_internet)
            return
        }
        fetchScheduleForAlbumNumber(Integer.valueOf(albumNumber))
    }

    private fun validateAlbumNumber(albumNumber: String) =
            if (albumNumber.isEmpty()) {
                view.showError(R.string.error_field_cannot_be_empty)
                false
            } else {
                true
            }

    private fun fetchScheduleForAlbumNumber(albumNumber: Int) {
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
        repository.save(days,
                {
                    user.save(albumNumber)
                    view.onDataSaved()
                },
                { onError(it) }
        )
    }

    private fun onError(error: Throwable) {
        val errorMessage = textProvider.getErrorMessageIdRes(error)
        view.showError(errorMessage)
        view.hideLoading()
    }

    override fun cancelFetch() = compositeDisposable.clear()
}
