package pl.edu.zut.mad.schedule.search

import android.support.annotation.StringRes
import io.reactivex.subjects.PublishSubject
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal interface SearchMvp {

    interface View {
        fun setData(lessons: List<Lesson>)

        fun showLoading()

        fun hideLoading()

        fun observeSearchInput(): PublishSubject<SearchInput>

        fun showError(@StringRes errorRes: Int)

        fun showSurnameSuggestions(surnames: List<String>)
    }

    interface Presenter {

        fun onDetach()

        fun onSurnameChange(text: String, field: String) {}
    }
}
