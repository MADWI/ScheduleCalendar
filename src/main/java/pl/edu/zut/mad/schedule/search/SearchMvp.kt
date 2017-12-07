package pl.edu.zut.mad.schedule.search

import android.support.annotation.StringRes
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal interface SearchMvp {

    interface View {
        fun setData(lessons: List<Lesson>)

        fun showLoading()

        fun hideLoading()

        fun getSearchQuery(): SearchInputViewModel

        fun showError(@StringRes errorRes: Int)
    }

    interface Presenter {
        fun onSearch()
    }
}
