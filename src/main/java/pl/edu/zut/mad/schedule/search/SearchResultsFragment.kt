package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_results.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

internal class SearchResultsFragment : Fragment() {

    companion object {
        private const val LESSONS_KEY = "lessons_key"

        fun newInstance(lessons: List<Lesson>): SearchResultsFragment {
            val searchResultsFragment = SearchResultsFragment()
            val arguments = Bundle()
            arguments.putParcelableArrayList(LESSONS_KEY, ArrayList(lessons))
            searchResultsFragment.arguments = arguments
            return searchResultsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lessons = arguments.getParcelableArrayList<Lesson>(LESSONS_KEY)
        lessonsListView.adapter = LessonsAdapter(lessons)
    }
}
