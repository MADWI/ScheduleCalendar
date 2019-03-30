package pl.edu.zut.mad.schedule.search

import androidx.annotation.StringRes
import io.reactivex.subjects.PublishSubject
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal interface SearchMvp {

    interface View {
        fun setData(lessons: List<Lesson>)

        fun showLoading()

        fun hideLoading()

        fun observeSearchInputModel(): PublishSubject<SearchInput>

        fun observeSearchInputText(): PublishSubject<Pair<String, String>>

        fun showError(@StringRes errorRes: Int)

        fun showSuggestions(suggestions: List<String>, filterField: String)
    }

    interface Presenter {

        fun onDetach()
    }
}
