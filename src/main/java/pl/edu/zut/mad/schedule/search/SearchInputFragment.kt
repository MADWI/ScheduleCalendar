package pl.edu.zut.mad.schedule.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_input.*
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject

internal class SearchInputFragment : Fragment(), SearchMvp.View {

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
        private val DATE_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT) // TODO move to config class
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

    override fun getDateFrom(): LocalDate =
        LocalDate.parse(dateFromView.text.toString(), DATE_FORMATTER)

    override fun getDateTo(): LocalDate =
        LocalDate.parse(dateToView.text.toString(), DATE_FORMATTER)

    override fun onScheduleDownloaded(lessons: List<Lesson>) {
        val searchResultsFragment = SearchResultsFragment.newInstance(lessons)
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.searchMainContainer, searchResultsFragment)
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
        searchButton.setOnClickListener {
            presenter.onSearch()
        }
        teacherNameInputView.setText("Piotr")
        teacherSurnameInputView.setText("Piela")
        facultyAbbreviationInputView.setText("WI")
        subjectInputView.setText("Modelowanie i symulacja systemów")
    }

    private fun initDatePickers() {
        val dateFrom = LocalDate.now()
        dateFromView.text = dateFrom.toString(DATE_FORMATTER)
        dateFromView.setOnClickListener {
            DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener
                { _, year, month, dayOfMonth -> dateFromView.text = parseDate(dayOfMonth, month, year) },
                dateFrom.year, dateFrom.monthOfYear - 1, dateFrom.dayOfMonth)
                .show()
        }
        val dateTo = dateFrom.plusDays(7)
        dateToView.text = dateTo.toString(DATE_FORMATTER)
        dateToView.setOnClickListener {
            DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener
                { _, year, month, dayOfMonth -> dateToView.text = parseDate(dayOfMonth, month, year) },
                dateTo.year, dateTo.monthOfYear - 1, dateTo.dayOfMonth)
                .show()
        }
    }

    private fun parseDate(dayOfMonth: Int, month: Int, year: Int): String? {
        val date = LocalDate()
            .withDayOfMonth(dayOfMonth)
            .withMonthOfYear(month + 1)
            .withYear(year)
        return DATE_FORMATTER.print(date)
    }
}
