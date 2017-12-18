package pl.edu.zut.mad.schedule.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.model.ui.Lesson

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val LESSON_KEY = "lesson_key"

        internal fun getIntentWithLesson(context: Context, lesson: Lesson): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            val extras = Bundle()
            extras.putParcelable(LESSON_KEY, lesson)
            intent.putExtras(extras)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if (savedInstanceState == null) {
            startSearchInputFragment()
        }
    }

    private fun startSearchInputFragment() {
        val searchInputFragment = getSearchInputFragmentWithArguments()
        supportFragmentManager.beginTransaction()
            .replace(R.id.searchMainContainer, searchInputFragment)
            .commit()
    }

    private fun getSearchInputFragmentWithArguments(): SearchInputFragment {
        val arguments = intent.extras
        val lesson = arguments?.getParcelable<Lesson>(LESSON_KEY)
        return if (lesson == null)
            SearchInputFragment()
        else SearchInputFragment.newInstance(lesson)
    }

    override fun onBackPressed() {
        val searchInputFragment = supportFragmentManager.findFragmentByTag(SearchInputFragment.TAG)
        if (searchInputFragment != null) {
            (searchInputFragment as SearchInputFragment).onBackPressed()
        } else {
            super.onBackPressed()
        }
    }
}
