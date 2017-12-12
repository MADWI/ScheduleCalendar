package pl.edu.zut.mad.schedule.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search_input.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.ScheduleDate
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.LessonIndexer
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject

internal class SearchInputFragment : Fragment(), SearchMvp.View {

    companion object {
        private const val LESSON_KEY = "lesson_key"

        fun newInstance(lesson: Lesson): SearchInputFragment {
            val inputFragment = SearchInputFragment()
            val arguments = Bundle()
            arguments.putParcelable(LESSON_KEY, lesson)
            inputFragment.arguments = arguments
            return inputFragment
        }
    }

    @Inject lateinit var presenter: SearchMvp.Presenter
    @Inject lateinit var lessonIndexer: LessonIndexer

    private val searchInputSubject by lazy {
        PublishSubject.create<SearchInput>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_input, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun showLoading() = searchButtonView.startAnimation()

    override fun hideLoading() = searchButtonView.revertAnimation()

    override fun showError(@StringRes errorRes: Int) {
        val contentView = activity.findViewById<View>(android.R.id.content)
        Snackbar.make(contentView, errorRes, Toast.LENGTH_SHORT).show()
    }

    override fun setData(lessons: List<Lesson>) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.searchMainContainer, SearchResultsFragment.newInstance(lessons))
            .addToBackStack(null)
            .commit()
    }

    override fun getSearchSubject(): PublishSubject<SearchInput> {
        return searchInputSubject
    }

    private fun init(savedInstanceState: Bundle?) {
        initInjections()
        initViews(savedInstanceState)
    }

    private fun initInjections() = app.component
        .plus(SearchModule(this))
        .inject(this)

    private fun initViews(savedInstanceState: Bundle?) {
        initDatePickers()
        searchButtonView.setOnClickListener {
            searchInputSubject.onNext(getSearchInput())
            presenter.onSearch()
        }
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

    private fun getOnDateSetListenerForView(textView: TextView): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val dateText = parseDate(dayOfMonth, month, year)
            textView.text = dateText
        }
    }

    private fun parseDate(dayOfMonth: Int, month: Int, year: Int): String {
        val date = LocalDate()
            .withDayOfMonth(dayOfMonth)
            .withMonthOfYear(month + 1)
            .withYear(year)
        return ScheduleDate.UI_FORMATTER.print(date)
    }

    private fun initInputViewsWithLessonArgument() {
        val lesson = arguments?.getParcelable<Lesson>(LESSON_KEY) ?: return
        with(lesson) {
            teacherNameInputView.setText(teacher.name)
            teacherSurnameInputView.setText(teacher.surname)
            facultyAbbreviationInputView.setText(facultyAbbreviation)
            roomInputView.setText(room)
            subjectInputView.setText(subject)
            fieldOfStudyInputView.setText(fieldOfStudy)
            val courseTypeSelection = lessonIndexer.getCourseTypeIndex(type)
            courseTypeSpinnerView.setSelection(courseTypeSelection)
            semesterSpinnerView.setSelection(semester)
        }
    }

    private fun getSearchInput(): SearchInput {
        return SearchInput(
            teacherNameInputView.text.toString(),
            teacherSurnameInputView.text.toString(),
            facultyAbbreviationInputView.text.toString(),
            subjectInputView.text.toString(),
            fieldOfStudyInputView.text.toString(),
            courseTypeSpinnerView.selectedItem?.toString() ?: "",
            semesterSpinnerView.selectedItem?.toString() ?: "",
            formSpinnerView.selectedItem?.toString() ?: "",
            dateFromView.text.toString(),
            dateToView.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchButtonView.dispose()
        presenter.onDetach()
    }
}
