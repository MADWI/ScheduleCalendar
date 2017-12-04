package pl.edu.zut.mad.schedule.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_search_input.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.ScheduleDate
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject

internal class SearchInputFragment : Fragment(), SearchMvp.View {

    companion object {
        private const val DAYS_IN_WEEK = 7
    }

    @Inject internal lateinit var presenter: SearchMvp.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_input, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun getTeacherName() = teacherNameInputView.text.toString()

    override fun getTeacherSurname() = teacherSurnameInputView.text.toString()

    override fun getFacultyAbbreviation() = facultyAbbreviationInputView.text.toString()

    override fun getSubject() = subjectInputView.text.toString()

    override fun getFieldOfStudy() = fieldOfStudyInputView.text.toString()

    override fun getCourseType() = courseTypeSpinnerView.selectedItem?.toString() ?: ""

    override fun getSemester(): Int? = semesterSpinnerView.selectedItem?.toString()?.toInt()

    override fun getForm() = formSpinnerView.selectedItem?.toString() ?: ""

    override fun getDateFrom() = dateFromView.text.toString()

    override fun getDateTo() = dateToView.text.toString()

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

    private fun init() {
        initInjections()
        initViews()
    }

    private fun initInjections() = app.component
        .plus(SearchModule(this))
        .inject(this)

    private fun initViews() {
        initDatePickers()
        searchButtonView.setOnClickListener { presenter.onSearch() }
    }

    private fun initDatePickers() {
        val dateFrom = LocalDate.now()
        dateFromView.setOnClickListener { getDatePickerDialog(dateFrom, dateFromView).show() }
        val dateTo = dateFrom.plusDays(DAYS_IN_WEEK)
        dateToView.setOnClickListener { getDatePickerDialog(dateTo, dateToView).show() }
    }

    private fun getDatePickerDialog(date: LocalDate, editText: EditText) =
        DatePickerDialog(context, getOnDateSetListenerForView(editText),
            date.year, date.monthOfYear - 1, date.dayOfMonth)

    private fun getOnDateSetListenerForView(editText: EditText): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val dateText = parseDate(dayOfMonth, month, year)
            editText.setText(dateText)
        }
    }

    private fun parseDate(dayOfMonth: Int, month: Int, year: Int): String {
        val date = LocalDate()
            .withDayOfMonth(dayOfMonth)
            .withMonthOfYear(month + 1)
            .withYear(year)
        return ScheduleDate.UI_FORMATTER.print(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchButtonView.dispose()
    }
}
