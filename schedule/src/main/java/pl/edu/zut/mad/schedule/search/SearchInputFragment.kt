package pl.edu.zut.mad.schedule.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search_input.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.ScheduleDate
import pl.edu.zut.mad.schedule.animation.AnimationParams
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject
import kotlin.math.sqrt
import kotlin.reflect.KProperty0

internal class SearchInputFragment : Fragment(), SearchMvp.View {

    companion object {
        const val TAG = "search_input_fragment_tag"
        private const val LESSON_KEY = "lesson_key"

        fun newInstance(lesson: Lesson): SearchInputFragment {
            val inputFragment = SearchInputFragment()
            val arguments = Bundle()
            arguments.putParcelable(LESSON_KEY, lesson)
            inputFragment.arguments = arguments
            return inputFragment
        }
    }

    @Inject
    lateinit var presenter: SearchMvp.Presenter

    private val searchInputModelSubject by lazy { PublishSubject.create<SearchInput>() }
    private val searchInputTextSubject by lazy { PublishSubject.create<Pair<String, String>>() }
    private val inputSuggestionThreshold by lazy {
        resources.getInteger(R.integer.search_input_text_threshold_default)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_input, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun showLoading() {
        searchButtonView.startAnimation()
        searchInputScrollView.fullScroll(View.FOCUS_DOWN)
        setEnabledForInputViews(false)
    }

    override fun hideLoading() {
        setEnabledForInputViews(true)
        searchButtonView.revertAnimation()
    }

    override fun showError(@StringRes errorRes: Int) {
        val contentView = requireActivity().findViewById<View>(android.R.id.content)
        Snackbar.make(contentView, errorRes, Snackbar.LENGTH_SHORT).show()
    }

    override fun setData(lessons: List<Lesson>) {
        val resultsFragment = getInitializedResultsFragment(lessons)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.searchMainContainer, resultsFragment, SearchResultsFragment.TAG)
            .commit()
    }

    override fun observeSearchInputModel(): PublishSubject<SearchInput> = searchInputModelSubject

    override fun observeSearchInputText(): PublishSubject<Pair<String, String>> = searchInputTextSubject

    override fun showSuggestions(suggestions: List<String>, filterField: String) {
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, suggestions)
        val suggestionView = view?.findViewWithTag<AutoCompleteTextView>(filterField) ?: return
        suggestionView.setAdapter(adapter)
        suggestionView.showDropDown()
    }

    private fun init(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        initInjections()
        initViews(savedInstanceState)
    }

    private fun initInjections() = app.component
        .plus(SearchModule(this))
        .inject(this)

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) =
        inflater.inflate(R.menu.search_menu, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search_advanced_mode) {
            showOrHideAdvancedViewAndSetCheckedMenuItem(item)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchButtonView.dispose()
        presenter.onDetach()
    }

    private fun initViews(savedInstanceState: Bundle?) {
        initDatePickers()
        initInputListeners()
        searchButtonView.setOnClickListener { searchInputModelSubject.onNext(getSearchInput()) }
        if (savedInstanceState == null) {
            initInputViewsWithLessonArgument()
        }
    }

    private fun initDatePickers() {
        dateFromView.setOnClickListener { getDatePickerDialog(dateFromView).show() }
        dateToView.setOnClickListener { getDatePickerDialog(dateToView).show() }
    }

    private fun getDatePickerDialog(textView: TextView): DatePickerDialog {
        val dateText = textView.text.toString()
        val date = if (dateText.isEmpty()) {
            LocalDate.now()
        } else {
            LocalDate.parse(dateText, ScheduleDate.UI_FORMATTER)
        }
        return DatePickerDialog(context, getOnDateSetListenerForView(textView),
            date.year, date.monthOfYear - 1, date.dayOfMonth)
    }

    private fun getOnDateSetListenerForView(textView: TextView) =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val dateText = parseDate(dayOfMonth, month, year)
            textView.text = dateText
        }

    private fun parseDate(dayOfMonth: Int, month: Int, year: Int): String {
        val date = LocalDate()
            .withDayOfMonth(dayOfMonth)
            .withMonthOfYear(month + 1)
            .withYear(year)
        return ScheduleDate.UI_FORMATTER.print(date)
    }

    private fun initInputListeners() {
        addListenerForView(teacherNameInputView, SearchInput::name.name)
        addListenerForView(teacherSurnameInputView, SearchInput::surname.name)
        addListenerForView(fieldOfStudyInputView, SearchInput::fieldOfStudy.name)
        addListenerForView(roomInputView, SearchInput::room.name)
        addListenerForView(subjectInputView, SearchInput::subject.name)
    }

    private fun addListenerForView(textView: TextView, fieldName: String) {
        textView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
            }

            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (text.length == inputSuggestionThreshold) {
                    searchInputTextSubject.onNext(Pair(fieldName, text.toString()))
                }
            }
        })
    }

    private fun initInputViewsWithLessonArgument() {
        val lesson = arguments?.getParcelable<Lesson>(LESSON_KEY) ?: return
        with(lesson) {
            setupViewWithTextAndTag(teacherNameInputView, teacher::name)
            setupViewWithTextAndTag(teacherSurnameInputView, teacher::surname)
            setupViewWithTextAndTag(subjectInputView, ::subject)
            setupViewWithTextAndTag(fieldOfStudyInputView, ::fieldOfStudy)
        }
    }

    private fun setupViewWithTextAndTag(textView: TextView, property: KProperty0<String>) =
        with(textView) {
            text = property.get()
            tag = property.name
        }

    private fun getSearchInput(): SearchInput {
        return SearchInput(
            teacherNameInputView.text.toString(),
            teacherSurnameInputView.text.toString(),
            facultySpinnerView.selectedItem?.toString() ?: "",
            subjectInputView.text.toString(),
            fieldOfStudyInputView.text.toString(),
            courseTypeSpinnerView.selectedItem?.toString() ?: "",
            semesterSpinnerView.selectedItem?.toString() ?: "",
            formSpinnerView.selectedItem?.toString() ?: "",
            roomInputView.text.toString(),
            dateFromView.text.toString(),
            dateToView.text.toString())
    }

    private fun setEnabledForInputViews(isEnabled: Boolean) {
        for (i in 0 until searchInputContainer.childCount) {
            searchInputContainer.getChildAt(i).isEnabled = isEnabled
        }
    }

    private fun getInitializedResultsFragment(lessons: List<Lesson>): SearchResultsFragment {
        val animationParams = getAnimationParamsForResultView()
        val searchResultsFragment = SearchResultsFragment.newInstance(lessons, animationParams)
        searchResultsFragment.dismissListener = { hideLoading() }
        return searchResultsFragment
    }

    private fun getAnimationParamsForResultView(): AnimationParams {
        val centerX = searchButtonView.x.toInt() + searchButtonView.width / 2
        val centerY = getSearchButtonViewYCenter()
        val width = view?.width ?: 0
        val height = view?.height ?: 0
        val startRadius = searchButtonView.height / 2
        val endRadius = sqrt(getPow2(width / 2) + getPow2(height))
        return AnimationParams(centerX, centerY, width, height, startRadius, endRadius.toInt())
    }

    private fun getSearchButtonViewYCenter(): Int {
        val viewLocation = IntArray(2)
        searchButtonView.getLocationOnScreen(viewLocation)
        val barHeight = (activity as AppCompatActivity).supportActionBar?.height ?: 0
        return viewLocation[1] - barHeight
    }

    private fun getPow2(value: Int) = Math.pow(value.toDouble(), 2.toDouble())

    private fun showOrHideAdvancedViewAndSetCheckedMenuItem(item: MenuItem) =
        if (searchAdvancedView.isExpanded) {
            searchAdvancedView.collapse()
            item.isChecked = false
        } else {
            searchAdvancedView.expand()
            item.isChecked = true
        }
}
