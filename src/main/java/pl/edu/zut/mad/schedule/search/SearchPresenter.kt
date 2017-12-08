package pl.edu.zut.mad.schedule.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.data.model.api.Day
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection

internal class SearchPresenter(private val view: SearchMvp.View,
    private val service: ScheduleService, private val modelMapper: ModelMapper,
    private val networkConnection: NetworkConnection, private val messageProvider: MessageProviderSearch)
    : SearchMvp.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun onSearch() {
        view.showLoading()
        if (!networkConnection.isAvailable()) {
            view.hideLoading()
            view.showError(R.string.error_no_internet)
            return
        }
        val disposable = view.loadSearchQuery()
            .subscribeOn(Schedulers.io())
            .map { modelMapper.toLessonsSearchQueryMap(it) }
            .flatMap { service.fetchScheduleByQueries(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showLessonsAndHideLoading(it) },
                { showErrorAndHideLoading(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onDetach() = compositeDisposable.clear()

    private fun showLessonsAndHideLoading(days: List<Day>) {
        view.hideLoading()
        val lessons = modelMapper.toUiLessons(days)
        view.setData(lessons)
    }

    private fun showErrorAndHideLoading(error: Throwable) {
        view.hideLoading()
        val errorResId = messageProvider.getResIdByError(error)
        view.showError(errorResId)
    }
}
