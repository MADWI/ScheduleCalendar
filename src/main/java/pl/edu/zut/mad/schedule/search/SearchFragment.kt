package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*
import pl.edu.zut.mad.schedule.R
import javax.inject.Inject

class SearchFragment : Fragment(), SearchMvp.View {

    @Inject lateinit var presenter: SearchMvp.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInjections()
        initViewsButton()
    }

    private fun initInjections() {
        DaggerSearchComponent.builder()
            .searchModule(SearchModule(this))
            .build()
            .inject(this)
        teacherNameInputView.setText("Piotr")
        teacherSurnameInputView.setText("Piela")
        facultyAbbreviationInputView.setText("WI")
        subjectInputView.setText("Modelowanie i symulacja systemów")
    }

    private fun initViewsButton() {
        searchButton.setOnClickListener {
            presenter.onSearch()
        }
    }

    override fun getTeacherName() = teacherNameInputView.text.toString()

    override fun getTeacherSurname() = teacherSurnameInputView.text.toString()

    override fun getFacultyAbbreviation() = facultyAbbreviationInputView.text.toString()

    override fun getSubject() = subjectInputView.text.toString()
}
