package pl.edu.zut.mad.schedule.login

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.User
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.MessageProviderLogin
import pl.edu.zut.mad.schedule.util.NetworkConnection

internal class LoginPresenter(private val view: LoginMvp.View, private val repository: ScheduleRepository,
    private val service: ScheduleService, private val network: NetworkConnection,
    private val messageProvider: MessageProviderLogin, private val user: User) : LoginMvp.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onDownloadScheduleClick() {
        val albumNumber = view.getAlbumNumberText()
        if (!validateAlbumNumber(albumNumber)) {
            return
        }
        if (!network.isAvailable()) {
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
        view.showLoading()
        val disposable = service.fetchScheduleByAlbumNumber(albumNumber)
            .subscribeOn(Schedulers.io())
            .flatMapCompletable { repository.save(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { view.hideLoading() }
            .subscribe(
                {
                    view.onDataSaved()
                    user.save(albumNumber)
                },
                { onError(it) }
            )
        compositeDisposable.add(disposable)
    }

    private fun onError(error: Throwable) {
        val errorResId = messageProvider.getResIdByError(error)
        view.showError(errorResId)
        view.hideLoading()
    }

    override fun cancelFetch() = compositeDisposable.clear()
}
