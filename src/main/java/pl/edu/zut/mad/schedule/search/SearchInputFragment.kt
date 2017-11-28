package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_input.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject

internal class SearchInputFragment : Fragment(), SearchMvp.View {

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

    override fun onScheduleDownloaded(lessons: List<Lesson>) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.searchMainContainer, SearchResultsFragment())
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
        searchButton.setOnClickListener {
            presenter.onSearch()
        }
        teacherNameInputView.setText("Piotr")
        teacherSurnameInputView.setText("Piela")
        facultyAbbreviationInputView.setText("WI")
        subjectInputView.setText("Modelowanie i symulacja systemów")
    }
}
