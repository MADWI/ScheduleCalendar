package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.edu.zut.mad.schedule.R

internal class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if (savedInstanceState == null) {
            startSearchFragment()
        }
    }

    private fun startSearchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.searchMainContainer, SearchInputFragment())
            .commit()
    }
}
