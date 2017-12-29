package pl.edu.zut.mad.schedule.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.ScheduleService
import pl.edu.zut.mad.schedule.util.ModelMapper
import pl.edu.zut.mad.schedule.util.NetworkConnection
import pl.edu.zut.mad.schedule.util.log

internal class SearchPresenter(private val view: SearchMvp.View,
    private val service: ScheduleService, private val modelMapper: ModelMapper,
    private val networkConnection: NetworkConnection, private val messageProvider: MessageProviderSearch)
    : SearchMvp.Presenter {

    private val compositeDisposable = CompositeDisposable()

    init {
        val disposableModel = view.observeSearchInputModel()
            .doOnNext { view.showLoading() }
            .subscribe { fetchSchedule(it) }
        compositeDisposable.add(disposableModel)

        val disposableText = view.observeSearchInputText()
            .subscribe { fetchSuggestions(it) }
        compositeDisposable.add(disposableText)
    }

    private fun fetchSchedule(searchInput: SearchInput) {
        if (!networkConnection.isAvailable()) {
            view.hideLoading()
            view.showError(R.string.error_no_internet)
            return
        }
        val searchQueryMap = modelMapper.toLessonsSearchQueryMap(searchInput)
        val disposable = service.fetchScheduleByQueries(searchQueryMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { view.hideLoading() }
            .subscribe(
                { view.setData(modelMapper.toUiLessons(it)) },
                { view.showError(messageProvider.getResIdByError(it)) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onDetach() = compositeDisposable.clear()

    private fun fetchSuggestions(query: Pair<String, String>) {
        if (query.second.length != 3) { //TODO extract number
            return
        }
        val filterField = query.first
        service.fetchSuggestions(filterField, hashMapOf(query))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.showSuggestions(it, filterField) },
                { log(it.toString()) }
            )
    }
}
