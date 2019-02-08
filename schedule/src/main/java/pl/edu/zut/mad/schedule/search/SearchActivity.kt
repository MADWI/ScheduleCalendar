package pl.edu.zut.mad.schedule.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import pl.edu.zut.mad.schedule.BackPressedListener
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
        init(savedInstanceState)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments.lastOrNull { it is BackPressedListener }
        if (fragment == null) {
            super.onBackPressed()
        } else {
            (fragment as BackPressedListener).onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            startSearchInputFragment()
        }
    }

    private fun startSearchInputFragment() {
        val searchInputFragment = getSearchInputFragmentWithArguments()
        supportFragmentManager.beginTransaction()
            .replace(R.id.searchMainContainer, searchInputFragment, SearchInputFragment.TAG)
            .commit()
    }

    private fun getSearchInputFragmentWithArguments(): SearchInputFragment {
        val arguments = intent.extras
        val lesson = arguments?.getParcelable<Lesson>(LESSON_KEY)
        return if (lesson == null)
            SearchInputFragment()
        else SearchInputFragment.newInstance(lesson)
    }
}
